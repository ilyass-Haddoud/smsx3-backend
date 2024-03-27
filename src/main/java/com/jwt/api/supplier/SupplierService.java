package com.jwt.api.supplier;

import com.jwt.api.role.Role;
import com.jwt.api.role.RoleService;
import com.jwt.api.twoFactorAuthentication.TwoFactorAuthenticationService;
import com.jwt.api.user.UserRepository;
import com.jwt.api.utils.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final TwoFactorAuthenticationService twoFactorAuthenticationService;


    @Autowired
    public SupplierService(SupplierRepository supplierRepository, UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, TwoFactorAuthenticationService twoFactorAuthenticationService) {
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }

    public List<Supplier> getSuppliers()
    {
        return this.supplierRepository.findAll();
    }

    public Supplier getSupplierById(Integer id) {
        return this.supplierRepository.findById(id).orElseThrow(()-> new RuntimeException("supplier not found"));
    }

    public ResponseEntity<String> login(String bpsaddeml, String bpspasse,String otpCode)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(bpsaddeml,bpspasse));
            Supplier authenticatedUser = this.supplierRepository.getSupplierByBpsaddeml(bpsaddeml);
            String secret = authenticatedUser.getSecret();
            boolean isOtpValid = twoFactorAuthenticationService.isOtpValid(secret, otpCode);
            if (!isOtpValid)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid supplier OTP code");
            }
            List<String> roles = new ArrayList<>();
            this.getSupplierById(authenticatedUser.getId()).getRoles().forEach(role -> {
                roles.add(role.getAuthority());
            });
            String token = this.jwtUtil.createToken(authenticatedUser,roles);
            return ResponseEntity.ok().body(token);
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid suppliername or password");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public Supplier register(Supplier supplier)
    {
        String email = supplier.getBpsaddeml();
        if(userRepository.getUserByEmail(email) != null) throw new DataIntegrityViolationException("duplicate");
        String encodedPassword = this.passwordEncoder.encode(supplier.getBpspasse());
        supplier.setBpspasse(encodedPassword);
        supplier.setSecret(this.twoFactorAuthenticationService.generateNewSecret());
        return this.supplierRepository.save(supplier);
    }


    public Supplier convertToSupplier(SupplierLoginDTO supplierLoginDTO)
    {
        return this.modelMapper.map(supplierLoginDTO, Supplier.class);
    }
}
