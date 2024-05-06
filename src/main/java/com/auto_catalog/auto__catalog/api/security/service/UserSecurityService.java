package com.auto_catalog.auto__catalog.api.security.service;

import com.auto_catalog.auto__catalog.api.security.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService {
    private final UserSecurityRepository userSecurityRepository;

    @Autowired
    public UserSecurityService(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }
}
