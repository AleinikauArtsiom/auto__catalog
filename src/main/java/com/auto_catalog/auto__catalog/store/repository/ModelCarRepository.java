package com.auto_catalog.auto__catalog.store.repository;

import com.auto_catalog.auto__catalog.store.entity.ModelCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelCarRepository extends JpaRepository<ModelCar, Long> {
}
