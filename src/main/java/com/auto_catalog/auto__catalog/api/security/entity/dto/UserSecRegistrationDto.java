package com.auto_catalog.auto__catalog.api.security.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserSecRegistrationDto {

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;


    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;


    @NotNull
    @Size(min = 2, max = 30)
   /* @Pattern.List({
            @Pattern(regexp = "^(?=.*[0-9]).+$", message = "Password must contain at least one digit"),
            @Pattern(regexp = "^(?=.*[a-z]).+$", message = "Password must contain at least one lowercase letter"),
            @Pattern(regexp = "^(?=.*[A-Z]).+$", message = "Password must contain at least one uppercase letter"),
            @Pattern(regexp = "^(?=.*[@#$%^&+=_-]).+$", message = "Password must contain at least one special character")
    })*/
    private String password;


    @NotNull
/*    @Pattern(regexp = "^.*@.*[a-zA-Z].*$"*//*, message = "Invalid email format"*//*)*/ //TODO: ИСПРАВИТЬ
    private String email;


    @NotNull
    @Size(min = 2, max = 20)
    private String login;
}
