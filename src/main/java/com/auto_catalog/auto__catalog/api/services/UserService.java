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

    public UserDto updateUser(UserDto userDto) throws BadRequestException {
        User updatedUser = getUserOrThrowException(userDto.getUserId());

        userRepository
                .findByLogin(userDto.getLogin())
                .filter(anotherUser -> !Objects.equals(anotherUser.getLogin(), updatedUser.getLogin()))
                .orElseThrow(() -> new BadRequestException("User with login " + updatedUser.getLogin() + "' already exists"));

        userRepository.findByEmail(userDto.getEmail())
                .filter(anotherUser -> !Objects.equals(anotherUser.getEmail(), updatedUser.getEmail()))
                .orElseThrow(() -> new BadRequestException("User with email " + updatedUser.getEmail() + "' already exists"));


        updatedUser.setLogin(userDto.getLogin());
        updatedUser.setFirstName(userDto.getFirstName());
        updatedUser.setLastName(userDto.getLastName());
        updatedUser.setEmail(userDto.getEmail());
        updatedUser.setPassword(userDto.getPassword());

         User newUser = userRepository.saveAndFlush(updatedUser);

        return userDtoFactory.makeUserDto(newUser);
    }
}
