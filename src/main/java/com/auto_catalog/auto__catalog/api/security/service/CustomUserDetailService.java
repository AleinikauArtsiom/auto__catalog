package com.auto_catalog.auto__catalog.api.security.service;

import com.auto_catalog.auto__catalog.api.security.entity.UserSecurity;
import com.auto_catalog.auto__catalog.api.security.repository.UserSecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserSecurityRepository userSecurityRepository;

    @Autowired
    public CustomUserDetailService(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserSecurity> securityInfoOptional = userSecurityRepository.findByUserLogin(username);
        if (securityInfoOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }
        UserSecurity userSecurity = securityInfoOptional.get();
        return User.builder()
                .username(userSecurity.getUserLogin())
                .password(userSecurity.getUserPassword())
                .disabled(userSecurity.getIsBlocked()) // Используем isBlocked как disabled
                .roles(userSecurity.getRole().toString())
                .build();
    }
}
