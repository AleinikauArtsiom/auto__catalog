package com.auto_catalog.auto__catalog.store.repository;

import com.auto_catalog.auto__catalog.store.entity.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {
    Optional<BodyType> findByName(String name);
}
