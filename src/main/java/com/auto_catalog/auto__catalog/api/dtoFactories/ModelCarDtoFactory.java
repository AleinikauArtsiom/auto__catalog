package com.auto_catalog.auto__catalog.api.dtoFactories;

import ch.qos.logback.core.model.Model;
import com.auto_catalog.auto__catalog.api.dto.BrandDto;
import com.auto_catalog.auto__catalog.api.dto.ModelCarDto;
import com.auto_catalog.auto__catalog.store.entity.ModelCar;
import org.springframework.stereotype.Component;

@Component
public class ModelCarDtoFactory {
    public ModelCarDto makeModelCarDtoFactory(ModelCar modelCar){
        return ModelCarDto.builder()
                .name(modelCar.getName()).build();

    }
}
