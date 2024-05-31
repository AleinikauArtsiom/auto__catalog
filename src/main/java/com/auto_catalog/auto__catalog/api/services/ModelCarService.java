package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.ListingDto;
import com.auto_catalog.auto__catalog.api.dto.ModelCarDto;
import com.auto_catalog.auto__catalog.api.dtoFactories.ModelCarDtoFactory;
import com.auto_catalog.auto__catalog.api.exception.NotFoundException;
import com.auto_catalog.auto__catalog.store.entity.Brand;
import com.auto_catalog.auto__catalog.store.entity.Listing;
import com.auto_catalog.auto__catalog.store.entity.ModelCar;
import com.auto_catalog.auto__catalog.store.repository.BrandRepository;
import com.auto_catalog.auto__catalog.store.repository.ModelCarRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ModelCarDto> getAllModelCars() {
        return modelCarRepository.findAll()
                .stream()
                .map(modelCarDtoFactory::makeModelCarDtoFactory)
                .collect(Collectors.toList());
    }

    public void deleteModelCarById(Long id) {
        getModelCarOrThrowException(id);
        modelCarRepository.deleteById(id);
    }

    private ModelCarDto getModelCarOrThrowException(Long id) {
        ModelCar modelCar = getModelCarOrThrowExceptionEntity(id);
        return modelCarDtoFactory.makeModelCarDtoFactory(modelCar);
    }

    private ModelCar getModelCarOrThrowExceptionEntity(Long id) {
        return modelCarRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Model->car with ID " + id + " not found"));
    }

    public ModelCarDto getModelCarById(Long id) {
        return getModelCarOrThrowException(id);
    }

    public ModelCarDto createModelCar(@Valid ModelCarDto modelCarDto) {
        // Найти или создать бренд по имени
        Brand brand = brandRepository.findByName(modelCarDto.getBrandName())
                .orElseGet(() -> {
                    Brand newBrand = new Brand();
                    newBrand.setName(modelCarDto.getBrandName());
                    return brandRepository.save(newBrand);
                });

        // Создать и сохранить ModelCar
        ModelCar modelCar = modelCarRepository.save(
                ModelCar.builder()
                        .name(modelCarDto.getName())
                        .brand(brand)
                        .build()
        );

        // Вернуть DTO
        return modelCarDtoFactory.makeModelCarDtoFactory(modelCar);
    }

    public boolean updateModelCar(ModelCarDto modelCarDto) {
        Optional<ModelCar> modelCarOptional = modelCarRepository.findById(modelCarDto.getModelId());
        if (modelCarOptional.isPresent()) {
            ModelCar modelCarFromDB = modelCarOptional.get();
            if (modelCarDto.getName() != null) {
                modelCarFromDB.setName(modelCarDto.getName());
            }
            if (modelCarDto.getBrandName() != null) {
                Optional<Brand> brandOptional = brandRepository.findByName(modelCarDto.getBrandName());
                brandOptional.ifPresent(modelCarFromDB::setBrand);
            }
            ModelCar updatedModelCar = modelCarRepository.save(modelCarFromDB);
            return updatedModelCar != null;
        }
        return false;
    }

}
