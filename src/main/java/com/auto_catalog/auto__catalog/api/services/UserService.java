package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.UserDto;
import com.auto_catalog.auto__catalog.api.dto.UserDtoUpdate;
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

import java.security.Principal;
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

    public Optional<UserDto> getInfoAboutCurrentUser(String email) {
        Optional<UserSecurity> userSecurity = userSecurityRepository.findByUserLogin(email);
        if (userSecurity.isEmpty()) {
            return Optional.empty();
        }
        User user = userSecurity.get().getUser();
        return Optional.of(userDtoFactory.makeUserDto(user));
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

    public void deleteSelf(Principal principal) {
        User user = userSecurityRepository.findByUserLogin(principal.getName())
                .map(UserSecurity::getUser)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
    }

    public UserDtoUpdate createUser(@Valid UserDtoUpdate userDtoUpdate) {
        User user = userRepository.saveAndFlush(
                User.builder()
                        .firstName(userDtoUpdate.getFirstName())
                        .lastName(userDtoUpdate.getLastName())
                        .email(userDtoUpdate.getEmail())
                        // .password(userDto.getPassword())
                        .build()
        );

        return userDtoFactory.makeUserDtoUpdate(user);
    }

    public boolean updateUser(UserDtoUpdate userDtoUpdate) {
        User userFromDB = userRepository.findById(userDtoUpdate.getUserId())
                .orElseThrow(() -> new NotFoundException("User with ID " + userDtoUpdate.getUserId() + " doesn't exist"));

        if (userDtoUpdate.getEmail() != null && !userDtoUpdate.getEmail().equals(userFromDB.getEmail())) {
            userRepository.findByEmail(userDtoUpdate.getEmail()).ifPresent(existingUser -> {
                throw new UserReqEmailException(userDtoUpdate.getEmail());
            });
        }
        boolean updated = false;

        if (userDtoUpdate.getFirstName() != null) {
            userFromDB.setFirstName(userDtoUpdate.getFirstName());
            updated = true;
        }
        if (userDtoUpdate.getLastName() != null) {
            userFromDB.setLastName(userDtoUpdate.getLastName());
            updated = true;
        }
        if (userDtoUpdate.getEmail() != null) {
            userFromDB.setEmail(userDtoUpdate.getEmail());
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

    public boolean updateSelf(UserDtoUpdate userDtoUpdate, Principal principal) {
        User userFromDB = userSecurityRepository.findByUserLogin(principal.getName())
                .map(UserSecurity::getUser)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userDtoUpdate.getEmail() != null && !userDtoUpdate.getEmail().equals(userFromDB.getEmail())) {
            userRepository.findByEmail(userDtoUpdate.getEmail()).ifPresent(existingUser -> {
                throw new UserReqEmailException(userDtoUpdate.getEmail());
            });
        }
        boolean updated = false;

        if (userDtoUpdate.getFirstName() != null) {
            userFromDB.setFirstName(userDtoUpdate.getFirstName());
            updated = true;
        }
        if (userDtoUpdate.getLastName() != null) {
            userFromDB.setLastName(userDtoUpdate.getLastName());
            updated = true;
        }
        if (userDtoUpdate.getEmail() != null) {
            userFromDB.setEmail(userDtoUpdate.getEmail());
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