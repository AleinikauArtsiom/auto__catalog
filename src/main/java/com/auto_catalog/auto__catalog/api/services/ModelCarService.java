package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.ModelCarDto;
import com.auto_catalog.auto__catalog.api.dtoFactories.ModelCarDtoFactory;
import com.auto_catalog.auto__catalog.api.exception.NotFoundException;
import com.auto_catalog.auto__catalog.store.entity.Brand;
import com.auto_catalog.auto__catalog.store.entity.ModelCar;
import com.auto_catalog.auto__catalog.store.repository.BrandRepository;
import com.auto_catalog.auto__catalog.store.repository.ModelCarRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelCarService {
    private final ModelCarRepository modelCarRepository;
    private final ModelCarDtoFactory modelCarDtoFactory;
    private final BrandRepository brandRepository;

    @Autowired
    public ModelCarService(ModelCarRepository modelCarRepository, ModelCarDtoFactory modelCarDtoFactory,
                           BrandRepository brandRepository ) {
        this.modelCarRepository = modelCarRepository;
        this.modelCarDtoFactory = modelCarDtoFactory;
        this.brandRepository = brandRepository;

    }
    public List<ModelCar> getAllModelCars() {
        return modelCarRepository.findAll();

    }

    public void deleteModelCarById(Long id) {
        getModelCarOrThrowException(id);
        modelCarRepository.deleteById(id);
    }
    private ModelCar getModelCarOrThrowException(Long id) {
        return modelCarRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("ModelCar with " + id + " doesn't exist"));
    }

    public ModelCar getModelCarById(Long id) {
        return getModelCarOrThrowException(id);
    }

    public ModelCarDto createModelCar(@Valid ModelCarDto modelCarDto) {
        Brand brand = brandRepository.findByName(modelCarDto.getBrand().getName());

        ModelCar modelCar = modelCarRepository.save(
                ModelCar.builder()
                        .name(modelCarDto.getName())
                        .brand(brand)
                        .build()
        );
        return modelCarDtoFactory.makeModelCarDtoFactory(modelCar);
    }
    public boolean updateModelCar(ModelCarDto modelCarDto) {
        Optional<ModelCar> modelCarOptional = modelCarRepository.findById(modelCarDto.getModelId());
        if (modelCarOptional.isPresent()) {
            ModelCar modelCarFromDB = modelCarOptional.get();
            if (modelCarDto.getName() != null) {
                modelCarFromDB.setName(modelCarDto.getName());
            }
            if (modelCarDto.getBrand() != null) {
                Brand brand = brandRepository.findByName(modelCarDto.getBrand().getName());
                if (brand != null) { // Проверяем, найден ли бренд
                    modelCarFromDB.setBrand(brand);
                }
            }
            ModelCar updatedModelCar = modelCarRepository.save(modelCarFromDB);
            return updatedModelCar != null; // Возвращаем true, если модель автомобиля успешно обновлена, иначе false
        }
        return false;
    }
}
