package com.auto_catalog.auto__catalog.api.dtoFactories;

import com.auto_catalog.auto__catalog.api.dto.UserDto;
import com.auto_catalog.auto__catalog.api.dto.UserDtoUpdate;
import com.auto_catalog.auto__catalog.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDtoFactory {
    private final ListingDtoFactory listingDtoFactory;

    @Autowired
    public UserDtoFactory(ListingDtoFactory listingDtoFactory) {
        this.listingDtoFactory = listingDtoFactory;
    }


    public UserDto makeUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setListingCount(user.getListingCount());
        userDto.setListings(user.getListings().stream()
                .map(listingDtoFactory::makeListingDto)
                .collect(Collectors.toList()));
        return userDto;
    }

    public UserDtoUpdate makeUserDtoUpdate(User user) {
        return UserDtoUpdate.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
