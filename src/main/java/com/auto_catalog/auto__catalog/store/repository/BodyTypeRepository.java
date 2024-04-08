package com.auto_catalog.auto__catalog.store.repository;


import com.auto_catalog.auto__catalog.store.model.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {
}
