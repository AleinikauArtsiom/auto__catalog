package com.auto_catalog.auto__catalog.api.dtoFactories;

import com.auto_catalog.auto__catalog.api.dto.BrandDto;
import com.auto_catalog.auto__catalog.store.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandDtoFactory {
    public BrandDto makeBrandFactory(Brand Brand) {
        return BrandDto.builder()
                .name(Brand.getName()).build();
    }
}
