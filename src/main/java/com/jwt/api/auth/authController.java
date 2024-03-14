package com.jwt.api.auth;

import com.jwt.api.user.User;
import com.jwt.api.user.UserLoginDTO;
import com.jwt.api.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class authController {
    private final UserService userService;

    @Autowired
    public authController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO)
    {
        return this.userService.login(userLoginDTO.getEmail(),userLoginDTO.getPassword());
    }
    @PostMapping("register")
    public User register(@RequestBody User user)
    {
        return this.userService.register(user);
    }
}
