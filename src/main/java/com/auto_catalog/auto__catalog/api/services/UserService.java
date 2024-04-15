package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.UserDto;
import com.auto_catalog.auto__catalog.api.dtoFactories.UserDtoFactory;
import com.auto_catalog.auto__catalog.api.exception.NotFoundException;
import com.auto_catalog.auto__catalog.store.entity.User;
import com.auto_catalog.auto__catalog.store.repository.UserRepository;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserDtoFactory userDtoFactory;

    @Autowired
    public UserService(UserRepository userRepository, UserDtoFactory userDtoFactory) {
        this.userRepository = userRepository;
        this.userDtoFactory = userDtoFactory;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long user_id) {
        return getUserOrThrowException(user_id);
    }

    private User getUserOrThrowException(Long user_id) {
        return userRepository
                .findById(user_id)
                .orElseThrow(() -> new NotFoundException("User with " + user_id + " doesn't exist"));
    }

    public void deleteUserById(Long user_id) {
        getUserOrThrowException(user_id);
        userRepository.deleteById(user_id);
    }


    public UserDto createUser(@Valid UserDto userDto) {

        User user = userRepository.saveAndFlush(
                User.builder()
                        .login(userDto.getLogin())
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .email(userDto.getEmail())
                        .password(userDto.getPassword())
                        .build()
        );

        return userDtoFactory.makeUserDto(user);
    }
    public boolean updateUser(User user){
        Optional<User> userFromDBOptional = userRepository.findById(user.getUserId());
        if(userFromDBOptional.isPresent()){
            User userFromDB = userFromDBOptional.get();
            if(user.getFirstName() != null){
                userFromDB.setFirstName(user.getFirstName());
            }
            if(user.getLastName()!= null){
                userFromDB.setLastName(user.getLastName());
            }
            if(user.getEmail()!= null){
                userFromDB.setEmail(user.getEmail());
            }
            if(user.getPassword()!= null){
                userFromDB.setPassword(user.getPassword());
            }
            if(user.getLogin()!= null){
                userFromDB.setLogin(user.getLogin());
            }
            User updateUser = userRepository.saveAndFlush(userFromDB);
            return userFromDB.equals(updateUser);
        }
        return false;
    }
}
