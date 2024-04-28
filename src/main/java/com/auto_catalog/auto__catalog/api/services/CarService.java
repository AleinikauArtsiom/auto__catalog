package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.exception.NotFoundException;
import com.auto_catalog.auto__catalog.store.entity.Car;
import com.auto_catalog.auto__catalog.store.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Car with ID " + id + " not found"));
    }

    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public boolean updateCar(Car car) {
        Optional<Car> carFromDBOptional = carRepository.findById(car.getCarId());
        if (carFromDBOptional.isPresent()) {
            Car carFromDB = carFromDBOptional.get();
            if (car.getModelCar() != null) {
                carFromDB.setModelCar(car.getModelCar());
            }
            if (car.getBodyType() != null) {
                carFromDB.setBodyType(car.getBodyType());
            }
            if (car.getYear() != null) {
                carFromDB.setYear(car.getYear());
            }
            if (car.getMileage() != null) {
                carFromDB.setMileage(car.getMileage());
            }
            if (car.getPrice() != null) {
                carFromDB.setPrice(car.getPrice());
            }
            if (car.getCondition() != null) {
                carFromDB.setCondition(car.getCondition());
            }
            Car updatedCar = carRepository.saveAndFlush(carFromDB);

            return carFromDB.equals(updatedCar);
        }
        return false;
    }

}
