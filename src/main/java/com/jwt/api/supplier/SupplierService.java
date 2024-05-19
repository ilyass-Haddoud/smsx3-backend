package com.jwt.api.supplier;

import com.jwt.api.claim.Claim;
import com.jwt.api.email.Email;
import com.jwt.api.email.EmailService;
import com.jwt.api.role.Role;
import com.jwt.api.role.RoleService;
import com.jwt.api.twoFactorAuthentication.TwoFactorAuthenticationService;
import com.jwt.api.user.UserRepository;
import com.jwt.api.utils.JwtUtil;
import jakarta.mail.MessagingException;
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
    private final RoleService roleService;
    private final EmailService emailService;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, TwoFactorAuthenticationService twoFactorAuthenticationService, RoleService roleService, EmailService emailService) {
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
        this.roleService = roleService;
        this.emailService = emailService;
    }

    public List<Supplier> getSuppliers()
    {
        return this.supplierRepository.findAll();
    }

    public Supplier updateSupplier(String email, boolean isDisactivated)
    {
        Supplier existingSupplier = this.supplierRepository.getSupplierByBpsaddeml(email);
        if(existingSupplier != null)
        {
            existingSupplier.setDisactivated(isDisactivated);
            return this.supplierRepository.save(existingSupplier);
        }else{
            throw  new RuntimeException("Supplier not found");
        }
    }

    public ResponseEntity<String> changePassword(String supplier_email, String current_password,String new_password) throws MessagingException {
        Supplier supplier = this.supplierRepository.getSupplierByBpsaddeml(supplier_email);
        if (supplier != null)
        {
            if(this.passwordEncoder.matches(current_password,supplier.getBpspasse()))
            {
                String newPasswordEncoded = this.passwordEncoder.encode(new_password);
                supplier.setBpspasse(newPasswordEncoded);
                this.supplierRepository.save(supplier);
                Email mail = new Email();
                mail.setRecipient(supplier.getBpsaddeml());
                mail.setSubject("Changement de mot de passe");
                mail.setMessageBody("Cher(e) " + supplier.getBpsfname() + " " + supplier.getBpslname() + ",\n\n"
                        + "Nous vous informons que votre mot de passe a été changé avec succès pour votre compte fournisseur.\n\n"
                        + "Si vous n'avez pas effectué ce changement, veuillez nous contacter immédiatement pour signaler cette activité suspecte.\n\n"
                        + "Cordialement,\n\nSage InvoiceHub");
                this.emailService.sendEmail(mail);
                return ResponseEntity.ok("Password changed successfully");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong given password");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        }

    }

    public Supplier getSupplierById(Integer id) {
        return this.supplierRepository.findById(id).orElseThrow(()-> new RuntimeException("supplier not found"));
    }

    public Supplier getSupplierByEmail(String email) {
        return this.supplierRepository.getSupplierByBpsaddeml(email);
    }

    public ResponseEntity<String> login(String bpsaddeml, String bpspasse,String otpCode)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(bpsaddeml,bpspasse));
            Supplier authenticatedUser = this.supplierRepository.getSupplierByBpsaddeml(bpsaddeml);

            //check if account is enabled
            if(authenticatedUser.isDisactivated())
            {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Le compte est désactivé. Veuillez contacter l'administrateur.");
            }

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

    public Supplier register(Supplier supplier) throws MessagingException {
        String email = supplier.getBpsaddeml();
        if(userRepository.getUserByEmail(email) != null) throw new DataIntegrityViolationException("duplicate");
        String encodedPassword = this.passwordEncoder.encode(supplier.getBpspasse());
        supplier.setBpspasse(encodedPassword);
        supplier.setSecret(this.twoFactorAuthenticationService.generateNewSecret());
        Role role = this.roleService.getRoleById(1);
        supplier.getRoles().add(role);
        Email mail = new Email();
        mail.setRecipient(supplier.getBpsaddeml());
        mail.setSubject("Activation de votre compte fournisseur");
        mail.setMessageBody("Cher(e) "+supplier.getBpsfname()+" "+supplier.getBpslname()+",\n\n"
                + "Nous vous remercions de votre inscription en tant que fournisseur sur notre plateforme.\n\n"
                + "Nous souhaitons vous informer que votre compte a été créé avec succès. Cependant, veuillez noter que votre compte est actuellement désactivé. Pour activer votre compte et accéder à notre plateforme, veuillez patienter pendant que notre administrateur examine votre demande et active votre compte.\n\n"
                + "Nous vous tiendrons informé(e) par e-mail dès que votre compte sera activé. En attendant, n'hésitez pas à nous contacter si vous avez des questions ou des préoccupations.\n\n"
                + "Cordialement,\n\nSage InvoiceHub");
        this.emailService.sendEmail(mail);
        return this.supplierRepository.save(supplier);
    }


    public Supplier convertToSupplier(SupplierLoginDTO supplierLoginDTO)
    {
        return this.modelMapper.map(supplierLoginDTO, Supplier.class);
    }
}
