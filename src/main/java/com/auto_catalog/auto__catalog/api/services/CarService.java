package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.CarDto;
import com.auto_catalog.auto__catalog.api.dtoFactories.CarDtoFactory;
import com.auto_catalog.auto__catalog.api.exception.NotFoundException;
import com.auto_catalog.auto__catalog.store.entity.BodyType;
import com.auto_catalog.auto__catalog.store.entity.Car;
import com.auto_catalog.auto__catalog.store.entity.ModelCar;
import com.auto_catalog.auto__catalog.store.repository.BodyTypeRepository;
import com.auto_catalog.auto__catalog.store.repository.CarRepository;
import com.auto_catalog.auto__catalog.store.repository.ModelCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final ModelCarRepository modelCarRepository;
    private final BodyTypeRepository bodyTypeRepository;
    private final CarDtoFactory carDtoFactory;

    @Autowired
    public CarService(CarRepository carRepository, ModelCarRepository modelCarRepository,
                      BodyTypeRepository bodyTypeRepository, CarDtoFactory carDtoFactory) {
        this.carRepository = carRepository;
        this.modelCarRepository = modelCarRepository;
        this.bodyTypeRepository = bodyTypeRepository;
        this.carDtoFactory = carDtoFactory;
    }

    public List<CarDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(carDtoFactory::makeCarDto)
                .collect(Collectors.toList());
    }

    public CarDto getCarById(Long id) {
       return getCarOrThrowException(id);
    }

    public void deleteCarById(Long id) {
        getCarOrThrowException(id);
       carRepository.deleteById(id);
    }

    private CarDto getCarOrThrowException(Long id) {
        Car car = getCarOrThrowExceptionEntity(id);
        return carDtoFactory.makeCarDto(car);
    }
    private Car getCarOrThrowExceptionEntity(Long id) {
        return carRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Car with ID " + id + " not found"));
    }

    public CarDto createCar(CarDto carDto) {
        ModelCar modelCar = modelCarRepository.findByName(carDto.getModelName())
                .orElseThrow(() -> new NotFoundException("ModelCar with name " + carDto.getModelName() + " not found"));
        BodyType bodyType = bodyTypeRepository.findByName(carDto.getBodyTypeName())
                .orElseThrow(() -> new NotFoundException("BodyType with name " + carDto.getBodyTypeName() + " not found"));

        Car car = Car.builder()
                .modelCar(modelCar)
                .bodyType(bodyType)
                .year(carDto.getYear())
                .mileage(carDto.getMileage())
                .price(carDto.getPrice())
                .condition(carDto.getCondition())
                .build();

        Car savedCar = carRepository.save(car);
        return carDtoFactory.makeCarDto(savedCar);
    }

    public boolean updateCar(CarDto carDto) {
        Optional<Car> carFromDBOptional = carRepository.findById(carDto.getCarId());
        if (carFromDBOptional.isPresent()) {
            Car carFromDB = carFromDBOptional.get();
            if (carDto.getModelName() != null) {
                ModelCar modelCar = modelCarRepository.findByName(carDto.getModelName())
                        .orElseThrow(() -> new NotFoundException("ModelCar with name " + carDto.getModelName() + " not found"));
                carFromDB.setModelCar(modelCar);
            }
            if (carDto.getBodyTypeName() != null) {
                BodyType bodyType = bodyTypeRepository.findByName(carDto.getBodyTypeName())
                        .orElseThrow(() -> new NotFoundException("BodyType with name " + carDto.getBodyTypeName() + " not found"));
                carFromDB.setBodyType(bodyType);
            }
            if (carDto.getYear() != null) {
                carFromDB.setYear(carDto.getYear());
            }
            if (carDto.getMileage() != null) {
                carFromDB.setMileage(carDto.getMileage());
            }
            if (carDto.getPrice() != null) {
                carFromDB.setPrice(carDto.getPrice());
            }
            if (carDto.getCondition() != null) {
                carFromDB.setCondition(carDto.getCondition());
            }
            Car updatedCar = carRepository.saveAndFlush(carFromDB);

            return updatedCar != null;
        }
        return false;
    }
}
