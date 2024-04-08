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
    @Size(min = 3, max = 20)
    private Brand brand;

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

}
