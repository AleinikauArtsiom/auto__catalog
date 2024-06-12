package com.auto_catalog.auto__catalog.api.controllers;

import com.auto_catalog.auto__catalog.api.dto.UserDto;
import com.auto_catalog.auto__catalog.api.dto.UserDtoUpdate;
import com.auto_catalog.auto__catalog.api.services.UserService;
import com.auto_catalog.auto__catalog.store.entity.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "Bearer Authentication")
@Transactional
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/info")
    public ResponseEntity<UserDto> getInfoAboutCurrentUser(Principal principal) {
        Optional<UserDto> result = userService.getInfoAboutCurrentUser(principal.getName());
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable Long user_id) {
        User user = userService.getUserById(user_id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{user_id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long user_id) {
        userService.deleteUserById(user_id);
        return ResponseEntity.noContent().build();
        // Artem lox
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SneakyThrows
    @PutMapping("/{user_id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserDtoUpdate userDtoUpdate) {
        return new ResponseEntity<>(userService.updateUser(userDtoUpdate) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDtoUpdate> createUser(@Valid @RequestBody UserDtoUpdate userDtoUpdate) {
        UserDtoUpdate createdUserDtoUpdate = userService.createUser(userDtoUpdate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDtoUpdate);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @SneakyThrows
    @PutMapping("/self")
    public ResponseEntity<HttpStatus> updateSelf(@RequestBody UserDtoUpdate userDtoUpdate, Principal principal) {
        return new ResponseEntity<>(userService.updateSelf(userDtoUpdate, principal) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/self")
    public ResponseEntity<HttpStatus> deleteSelf(Principal principal) {
        userService.deleteSelf(principal);
        return ResponseEntity.noContent().build();
    }
}