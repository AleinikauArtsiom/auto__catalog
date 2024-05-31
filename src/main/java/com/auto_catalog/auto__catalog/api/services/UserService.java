package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.UserDto;
import com.auto_catalog.auto__catalog.api.dtoFactories.UserDtoFactory;
import com.auto_catalog.auto__catalog.api.exception.NotFoundException;
import com.auto_catalog.auto__catalog.api.exception.UserReqEmailException;
import com.auto_catalog.auto__catalog.api.security.entity.UserSecurity;
import com.auto_catalog.auto__catalog.api.security.repository.UserSecurityRepository;
import com.auto_catalog.auto__catalog.store.entity.User;
import com.auto_catalog.auto__catalog.store.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoFactory userDtoFactory;
    private final UserSecurityRepository userSecurityRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserDtoFactory userDtoFactory, UserSecurityRepository userSecurityRepository) {
        this.userRepository = userRepository;
        this.userDtoFactory = userDtoFactory;
        this.userSecurityRepository = userSecurityRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getInfoAboutCurrentUser(String email) {
        Optional<UserSecurity> userSecurity = userSecurityRepository.findByUserLogin(email);
        if (userSecurity.isEmpty()) {
            return Optional.empty();
        }
        return userRepository.findById(userSecurity.get().getUser().getUserId());
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
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .email(userDto.getEmail())
                        // .password(userDto.getPassword())
                        .build()
        );

        return userDtoFactory.makeUserDto(user);
    }
    public boolean updateUser(UserDto userDto){
        User userFromDB = userRepository.findById(userDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User with ID " + userDto.getUserId() + " doesn't exist"));

        if (userDto.getEmail() != null && !userDto.getEmail().equals(userFromDB.getEmail())) {
            userRepository.findByEmail(userDto.getEmail()).ifPresent(existingUser -> {
                throw new UserReqEmailException(userDto.getEmail());
            });
        }
        boolean updated = false;

        if(userDto.getFirstName() != null){
            userFromDB.setFirstName(userDto.getFirstName());
            updated = true;
        }
        if(userDto.getLastName() != null){
            userFromDB.setLastName(userDto.getLastName());
            updated = true;
        }
        if(userDto.getEmail() != null){
            userFromDB.setEmail(userDto.getEmail());
            updated = true;
        }
    /*
    if(userDto.getPassword() != null){
        userFromDB.setPassword(userDto.getPassword());
        updated = true;
    }
    */

        if (updated) {
            userRepository.saveAndFlush(userFromDB);
        }

        return updated;
    }

}