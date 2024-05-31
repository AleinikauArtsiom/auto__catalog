package com.auto_catalog.auto__catalog.api.dto;


import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Id
    private Long userId;

   /* @NotNull
    private String login;*/

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    //@Pattern(regexp = "^.*@.*[a-zA-Z].*$", message = "Invalid email format")
    private String email;

    /*@NotNull
    private String password;*/

}
