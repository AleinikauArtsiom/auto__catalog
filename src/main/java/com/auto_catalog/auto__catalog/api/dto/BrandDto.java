package com.auto_catalog.auto__catalog.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {
    @NotNull
    private Long brandId;

    @NotNull
    private String name;
}
