package com.auto_catalog.auto__catalog.api.controllers;

import com.auto_catalog.auto__catalog.api.dto.UserDto;
import com.auto_catalog.auto__catalog.api.services.UserService;
import com.auto_catalog.auto__catalog.store.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Transactional
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable Long user_id) {
        User user= userService.getUserById(user_id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity deleteUserById(@PathVariable Long user_id) {
        userService.deleteUserById(user_id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUserDto = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @SneakyThrows
    @PutMapping("/{user_id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long user_id, @Valid @RequestBody UserDto userDto) {
        UserDto updatedUserDto = userService.updateUser(userDto);
        return ResponseEntity.ok(updatedUserDto);
    }
}




