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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Transactional
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/info")
    public ResponseEntity<User> getInfoAboutCurrentUser(Principal principal){
        Optional<User> result = userService.getInfoAboutCurrentUser(principal.getName());
        if (result.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasAnyRole('USER')")
    @GetMapping("/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable Long user_id) {
        User user= userService.getUserById(user_id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long user_id) {
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
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, @PathVariable Long user_id) {
        return new ResponseEntity<>(userService.updateUser(user) ? HttpStatus.NO_CONTENT :
                HttpStatus.CONFLICT);
    }
}




