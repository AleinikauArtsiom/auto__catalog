package com.auto_catalog.auto__catalog.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull
    private Long userId;

    @NotNull
    @Size(min = 5, max = 15)
    private String login;

    @NotNull
    @Size(min = 5)
    private String firstName;

    @NotNull
    @Size(min = 5)
    private String lastName;

    @NotNull
    @Pattern(regexp = "^.*@.*[a-zA-Z].*$", message = "Invalid email format")
    private String email;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

}
