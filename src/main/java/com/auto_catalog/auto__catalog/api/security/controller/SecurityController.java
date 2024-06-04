package com.auto_catalog.auto__catalog.api.security.controller;

import com.auto_catalog.auto__catalog.api.security.entity.dto.AuthRequestDto;
import com.auto_catalog.auto__catalog.api.security.entity.dto.AuthResponseDto;
import com.auto_catalog.auto__catalog.api.security.entity.dto.UserSecRegistrationDto;
import com.auto_catalog.auto__catalog.api.security.service.UserSecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
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
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        userSecurityService.registration(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

     @PostMapping("/admin/{id}")
     public void promoteUserToAdmin(@PathVariable Long  id){
        userSecurityService.promoteUserToAdmin(id);

 }
    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequestDto authRequestDto) {
        try {
            Optional<String> token = userSecurityService.generateToken(authRequestDto);
            if (token.isPresent()) {
                return ResponseEntity.ok(new AuthResponseDto(token.get()));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (IllegalArgumentException e) {
            if ("User is blocked".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is blocked");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }



    @PostMapping("/admin/block/{id}")
    public ResponseEntity<?> blockUser(@PathVariable Long id) {
        userSecurityService.blockUserById(id);
        return ResponseEntity.ok("User blocked successfully");
    }
}
