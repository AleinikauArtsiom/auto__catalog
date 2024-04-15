package com.auto_catalog.auto__catalog.store.repository;


import com.auto_catalog.auto__catalog.store.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByName(String name);
}

