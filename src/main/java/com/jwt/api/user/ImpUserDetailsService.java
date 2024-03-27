package com.jwt.api.user;

import com.jwt.api.supplier.Supplier;
import com.jwt.api.supplier.SupplierRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImpUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;

    public ImpUserDetailsService(UserRepository userRepository, SupplierRepository supplierRepository) {
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);
        if (user != null) {
            System.out.println("INSIDE USER CODE");
            System.out.println(user.toString());
            List<String> roles = new ArrayList<>();
            roles.add("USER");
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(roles.toArray(new String[0]))
                    .build();
        }

        Supplier supplier = supplierRepository.getSupplierByBpsaddeml(username);
        if (supplier != null) {
            System.out.println("INSIDE SUPPLIER CODE");
            System.out.println(supplier.toString());
            List<String> roles = new ArrayList<>();
            roles.add("SUPPLIER");
            return org.springframework.security.core.userdetails.User.builder()
                    .username(supplier.getBpsaddeml())
                    .password(supplier.getBpspasse())
                    .roles(roles.toArray(new String[0]))
                    .build();
        }

        throw new UsernameNotFoundException("Invalid username or password from userDetailsService");
    }
}
