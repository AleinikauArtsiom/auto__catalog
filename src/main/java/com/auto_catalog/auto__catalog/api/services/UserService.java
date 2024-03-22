package com.auto_catalog.auto__catalog.api.service;

import model.User;
import repository.UserRepository;

import java.util.List;

public class UserService {




    private UserRepository userRepository;
    public User findById(Long id){
        return userRepository.getOne(id);
    }
    public List<User> findAll(){
        return null;
    }
    //и так далее
    public User saveUser(User user){
        return null;
    }
    public void deleteUserById(Long id){

    }

}
