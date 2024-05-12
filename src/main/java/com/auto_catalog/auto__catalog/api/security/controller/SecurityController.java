package com.auto_catalog.auto__catalog.api.security.controller;

import com.auto_catalog.auto__catalog.api.security.entity.dto.AuthRequestDto;
import com.auto_catalog.auto__catalog.api.security.entity.dto.AuthResponseDto;
import com.auto_catalog.auto__catalog.api.security.entity.dto.UserSecRegistrationDto;
import com.auto_catalog.auto__catalog.api.security.service.UserSecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/security")
public class SecurityController {
    private final UserSecurityService userSecurityService;

    @Autowired
    public SecurityController(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody UserSecRegistrationDto registrationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Обработка ошибок валидации
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        // Если данные прошли валидацию, продолжаем выполнение метода
        userSecurityService.registration(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* @PostMapping("/admin")
     public ResponseEntity<HttpStatus> changeRoleToAdmin(Long id )

 }*/
    @PostMapping("/token")
    public ResponseEntity<AuthResponseDto> generateToken(@RequestBody AuthRequestDto authRequestDto) {
        Optional<String> token = userSecurityService.generateToken(authRequestDto);
        if(token.isPresent()){
            return new ResponseEntity<>(new AuthResponseDto(token.get()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
