package com.jwt.api.auth;

import com.jwt.api.twoFactorAuthentication.TwoFactorAuthenticationService;
import com.jwt.api.user.User;
import com.jwt.api.user.UserLoginDTO;
import com.jwt.api.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class authController {
    private final UserService userService;
    private final TwoFactorAuthenticationService twoFactorAuthenticationService;

    @Autowired
    public authController(UserService userService, TwoFactorAuthenticationService twoFactorAuthenticationService) {
        this.userService = userService;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }

    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO,@RequestParam String otpCode)
    {
        return this.userService.login(userLoginDTO.getEmail(),userLoginDTO.getPassword(),otpCode);
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody User user)
    {
        User registredUser =  this.userService.register(user);
        return ResponseEntity.ok(this.twoFactorAuthenticationService.generateQrCodeImageUri(registredUser.getSecret()));

    }
}
