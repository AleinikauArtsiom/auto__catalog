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
public class BodyTypeDto {
    private Long bodyTypeId;
    @NotNull
    private String name;

}
