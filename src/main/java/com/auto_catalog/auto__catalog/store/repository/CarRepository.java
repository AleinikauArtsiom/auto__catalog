package com.auto_catalog.auto__catalog.store.repository;

import com.auto_catalog.auto__catalog.store.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
