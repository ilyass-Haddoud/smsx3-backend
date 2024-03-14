package com.jwt.api.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRoles()
    {
        return this.roleRepository.findAll();
    }

    public Role getRoleById(Integer id) {
        return this.roleRepository.findById(id).orElseThrow(()-> new RuntimeException("role not found"));
    }

    public Role addRole(Role role)
    {
        return this.roleRepository.save(role);
    }
}
