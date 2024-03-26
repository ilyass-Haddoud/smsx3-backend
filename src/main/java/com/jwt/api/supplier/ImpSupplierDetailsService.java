package com.jwt.api.supplier;

import com.jwt.api.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImpSupplierDetailsService implements UserDetailsService {
    private final SupplierRepository supplierRepository;

    public ImpSupplierDetailsService( SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier supplier = supplierRepository.getSupplierByBpsaddeml(username);
        if(supplier == null) throw new UsernameNotFoundException("Invalid username or password");
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        return org.springframework.security.core.userdetails.User.builder()
                .username(supplier.getBpsaddeml())
                .password(supplier.getBpspasse())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}
