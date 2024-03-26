package com.jwt.api.config;

import com.jwt.api.filter.JwtFilter;
import com.jwt.api.supplier.ImpSupplierDetailsService;
import com.jwt.api.user.ImpUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
    private final ImpSupplierDetailsService impSupplierDetailsService;
    private final ImpUserDetailsService impUserDetailsService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(ImpSupplierDetailsService impSupplierDetailsService, ImpUserDetailsService impUserDetailsService, JwtFilter jwtFilter) {
        this.impSupplierDetailsService = impSupplierDetailsService;
        this.impUserDetailsService = impUserDetailsService;
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(impUserDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        request->{
                            request.requestMatchers("/auth/**").permitAll();
                            request.requestMatchers("/error").permitAll();
                            request.requestMatchers("/favicon.ico").permitAll();
                            request.anyRequest().authenticated();
                        }
                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
                .build();
    }
}
