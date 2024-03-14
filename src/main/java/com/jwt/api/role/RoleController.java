package com.jwt.api.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getRoles()
    {
        return this.roleService.getRoles();
    }

    @PostMapping
    public Role addRole(@RequestBody Role role)
    {
        return this.roleService.addRole(role);
    }
}
