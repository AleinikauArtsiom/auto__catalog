package com.auto_catalog.auto__catalog.store.repository;

import com.auto_catalog.auto__catalog.store.model.ModelCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelCarRepository extends JpaRepository<ModelCar, Long> {
}
