package com.auto_catalog.auto__catalog.api.dtoFactories;

import com.auto_catalog.auto__catalog.api.dto.ModelCarDto;
import com.auto_catalog.auto__catalog.store.entity.ModelCar;
import org.springframework.stereotype.Component;

@Component
public class ModelCarDtoFactory {
    public ModelCarDto makeModelCarDtoFactory(ModelCar modelCar) {
        return ModelCarDto.builder()
                .modelId(modelCar.getModelId())
                .brandName(modelCar.getBrand().getName())
                .name(modelCar.getName())
                .build();
    }
}
