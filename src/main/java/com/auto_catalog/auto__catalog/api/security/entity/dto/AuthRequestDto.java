package com.auto_catalog.auto__catalog.api.security.entity.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthRequestDto {
    private String login;
    private String password;
}
