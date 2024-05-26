package com.auto_catalog.auto__catalog.api.dtoFactories;

import com.auto_catalog.auto__catalog.api.dto.UserDto;
import com.auto_catalog.auto__catalog.store.entity.Listing;
import com.auto_catalog.auto__catalog.store.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDtoFactory {

    public UserDto makeUserDto(User user){
        return UserDto.builder()
                .userId(user.getUserId())
               // .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
               // .password(user.getPassword())
                /*.listingCount(user.getListingCount())
                .listingIds(user.getListings().stream().map(Listing::getListingId).collect(Collectors.toList()))*/
                .build();

    }
}
