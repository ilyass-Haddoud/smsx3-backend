package com.jwt.api.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


public class UserLoginDTO {
    @NotBlank(message = "email field cant be blank")
    @NotNull(message = "email field cant be null")
    @Email(message = "Not a valid email address")
    private String email;
    @NotBlank(message = "password field cant be blank")
    @NotNull(message = "password field cant be null")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
