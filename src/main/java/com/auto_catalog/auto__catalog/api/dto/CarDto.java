package com.auto_catalog.auto__catalog.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Long carId;

    private String modelName;

    private String bodyTypeName;

    private Integer year;

    private Integer mileage;

    private Integer price;

    private String condition;
}
