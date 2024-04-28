package com.auto_catalog.auto__catalog.api.dtoFactories;

import com.auto_catalog.auto__catalog.api.dto.CarDto;
import com.auto_catalog.auto__catalog.store.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarDtoFactory {

    public CarDto makeCarDto(Car car) {
        return CarDto.builder()
                .carId(car.getCarId())
                .modelCar(car.getModelCar())
                .bodyType(car.getBodyType())
                .year(car.getYear())
                .mileage(car.getMileage())
                .price(car.getPrice())
                .condition(car.getCondition())
                .build();
    }
}
