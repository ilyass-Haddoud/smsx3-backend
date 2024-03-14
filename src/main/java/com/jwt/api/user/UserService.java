package com.jwt.api.user;

import com.jwt.api.role.Role;
import com.jwt.api.role.RoleService;
import com.jwt.api.utils.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public List<User> getUsers()
    {
        return this.userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return this.userRepository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
    }

    public ResponseEntity<String> login(String email, String password)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            User authenticatedUser = this.userRepository.getUserByEmail(email);
            List<String> roles = new ArrayList<>();
            this.getUserById(authenticatedUser.getId()).getRoles().forEach(role -> {
                roles.add(role.getAuthority());
            });
            String token = this.jwtUtil.createToken(authenticatedUser,roles);
            return ResponseEntity.ok().body(token);
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid username or password");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public User register(User user)
    {
        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return this.userRepository.save(user);
    }

    public User addRoleToUser(int userId,int roleId)
    {
        User user = this.getUserById(userId);
        Role role = this.roleService.getRoleById(roleId);
        user.getRoles().add(role);
        return this.userRepository.save(user);
    }

    public User convertToUser(UserLoginDTO userLoginDTO)
    {
        return this.modelMapper.map(userLoginDTO,User.class);
    }
}
