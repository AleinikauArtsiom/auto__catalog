package com.auto_catalog.auto__catalog.api.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoUpdate {
    @Id
    private Long userId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    //@Pattern(regexp = "^.*@.*[a-zA-Z].*$", message = "Invalid email format")
    private String email;

}

