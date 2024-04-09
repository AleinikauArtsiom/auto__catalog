package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dtoFactories.ModelCarDtoFactory;
import com.auto_catalog.auto__catalog.store.repository.ModelCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelCarService {
    private final ModelCarRepository modelCarRepository;
    private final ModelCarDtoFactory modelCarDtoFactory;

    @Autowired
    public ModelCarService(ModelCarRepository modelCarRepository, ModelCarDtoFactory modelCarDtoFactory) {
        this.modelCarRepository = modelCarRepository;
        this.modelCarDtoFactory = modelCarDtoFactory;
    }

}
