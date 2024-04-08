package com.auto_catalog.auto__catalog.store.repository;


import com.auto_catalog.auto__catalog.store.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}

