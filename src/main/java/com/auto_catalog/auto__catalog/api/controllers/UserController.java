package com.auto_catalog.auto__catalog.api.controllers;

import com.auto_catalog.auto__catalog.api.services.UserService;
import com.auto_catalog.auto__catalog.store.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public class UserController {
   private UserService userService;

   private static final String GET_USER_BY_ID = "/api/users/{user_id}";

   @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("{GET_USER_BY_ID}")
    public String getUserById(@PathVariable Long user_id, HttpServletResponse response, ModelMap modelMap) {
       
    }
}





