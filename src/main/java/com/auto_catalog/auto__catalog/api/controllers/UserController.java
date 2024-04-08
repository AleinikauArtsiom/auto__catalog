package com.auto_catalog.auto__catalog.api.controllers;

import com.auto_catalog.auto__catalog.api.dto.UserDto;
import com.auto_catalog.auto__catalog.api.exception.CustomValidException;
import com.auto_catalog.auto__catalog.api.services.UserService;
import com.auto_catalog.auto__catalog.store.model.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller

public class UserController {
    private final UserService userService;

    private final static String UPDATE_USER = "/api/update/user/{id}";
    private final static String FIND_ID_USER = "/api/find/user/{id}";
    private final static String CREATE_USER = "/api/create/user";
    private final static String DELETE_USER_BY_ID = "/api/delete/user/{id}";
    private final static String FIND_ALL_USER = "/api/find/all/user";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(FIND_ID_USER)
    public String getUserById(@PathVariable("id") Long id, ModelMap modelMap) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            modelMap.addAttribute("user", user.get());
            System.out.println("------------------------------------------------------");
            System.out.println("User found: " + user.get());
            System.out.println("------------------------------------------------------");
            return "geet_user_by_id";
        }
        return "empty";
    }

    @GetMapping(FIND_ALL_USER)
    public String getAllUsers(ModelAndView modelAndView) {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println("----------------------------------------------------------");
            System.out.println("User: " + user);
            System.out.println("----------------------------------------------------------");
        }
        return "get_users";
    }

    @PostMapping(DELETE_USER_BY_ID)
    public String deleteUserById(@PathVariable("id") Long id) {
        if (userService.isUserExists(id)) {
            if (userService.deleteUserById(id)) {
                return "success";
            }
        } else {
            return "failure";
        }
        return "failure";
    }

    @PutMapping(UPDATE_USER)
    public ResponseEntity<HttpStatus> updateUserById(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);

    }

   /* @GetMapping("/registration")
    public String creUser(ModelMap modelMap) {
        modelMap.addAttribute("userDto", new UserDto());
        return "registration";
    }*/

    @PostMapping("/registration")
    public String createUser(@ModelAttribute @Valid UserDto userDto, ModelMap modelMap, BindingResult bindingResult, HttpServletResponse response) {
        modelMap.addAttribute("userDto", userDto);
        modelMap.addAttribute("errors", bindingResult.getAllErrors());

        if (userService.createUser(userDto)) {
            response.setStatus(201);
            return "success";

        } else {
            response.setStatus(409);
            return "failure";
           // return "redirect:/registration";
        }
    }
}





