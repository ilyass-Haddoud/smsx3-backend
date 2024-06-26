package com.jwt.api.config;

import com.jwt.api.filter.JwtFilter;
import com.jwt.api.user.ImpUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
    private final ImpUserDetailsService impUserDetailsService;
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(ImpUserDetailsService impUserDetailsService, JwtFilter jwtFilter) {
        this.impUserDetailsService = impUserDetailsService;
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(impUserDetailsService);
        //auth.setUserDetailsService(impSupplierDetailsService);
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
                            request.requestMatchers("/mail/**").permitAll();
                            request.requestMatchers("/suppliers/forgot_password").permitAll();
                            request.requestMatchers("/suppliers/reset_password").permitAll();
                            request.requestMatchers("/invoices/call/**").permitAll();
                            request.requestMatchers("/invoices/sage/addInvoice").permitAll();
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
