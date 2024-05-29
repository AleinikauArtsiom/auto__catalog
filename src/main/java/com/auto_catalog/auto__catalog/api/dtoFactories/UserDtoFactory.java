package com.auto_catalog.auto__catalog.api.dtoFactories;

import com.auto_catalog.auto__catalog.api.dto.UserDto;
import com.auto_catalog.auto__catalog.store.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoFactory {

    public UserDto makeUserDto(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
               // .password(user.getPassword())
                .build();
    }
}
