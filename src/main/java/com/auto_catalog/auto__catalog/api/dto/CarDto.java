package com.auto_catalog.auto__catalog.api.dto;

import com.auto_catalog.auto__catalog.store.entity.BodyType;
import com.auto_catalog.auto__catalog.store.entity.ModelCar;
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

    private ModelCar modelCar;

    private BodyType bodyType;

    private Integer year;

    private Integer mileage;

    private Integer price;

    private String condition;
}
