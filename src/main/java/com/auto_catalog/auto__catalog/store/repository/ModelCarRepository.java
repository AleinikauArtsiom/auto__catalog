package com.auto_catalog.auto__catalog.repository;

import model.ModelCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelCarRepository extends JpaRepository<ModelCar, Long> {
}
