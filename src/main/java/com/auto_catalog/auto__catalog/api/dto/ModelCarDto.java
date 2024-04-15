package com.auto_catalog.auto__catalog.api.dto;

import com.auto_catalog.auto__catalog.store.entity.Brand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelCarDto {
    @NotNull
    private Long modelId;

    @NotNull
    private Brand brand;

    @NotNull
    private String name;

}
