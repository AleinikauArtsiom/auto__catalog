package com.auto_catalog.auto__catalog.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelCarDto {
    @NotNull
    private Long modelId;

    @NotNull
    private String brandName;

    @NotNull
    private String name;

}
