package com.jwt.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers()
    {
        return this.userService.getUsers();
    }

    @GetMapping("/{user_id}/roles/{role_id}")
    public User addRoleToUser(@PathVariable Integer user_id,@PathVariable Integer role_id)
    {
        return this.userService.addRoleToUser(user_id,role_id);
    }

}
