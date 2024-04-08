package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.BodyTypeDto;
import com.auto_catalog.auto__catalog.api.dto.BrandDto;
import com.auto_catalog.auto__catalog.store.model.BodyType;
import com.auto_catalog.auto__catalog.store.model.Brand;
import com.auto_catalog.auto__catalog.store.repository.BodyTypeRepository;
import com.auto_catalog.auto__catalog.store.repository.BrandRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    public List<Brand> getAllBrand() {
        return brandRepository.findAll();

    }

    public boolean deleteBrandById(Long id) {
        brandRepository.deleteById(id);
        return getBrandById(id).isEmpty();
    }



    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);

    }

    public boolean createBrand(@Valid BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setName(brandDto.getName());
        Brand createdBrand = brandRepository.save(brand);
        return getBrandById(createdBrand.getBrandId()).isPresent();
    }

    public boolean updateBrand(Brand brand) {
        Optional<Brand> brandFromDBOptional = (brandRepository.findById(brand.getBrandId()));
        if (brandFromDBOptional.isPresent()) {
            Brand brandFromDB = brandFromDBOptional.get();
            if (brand.getName() != null) {
                brandFromDB.setName(brand.getName());
            }
            Brand updatedBrand = brandRepository.save(brandFromDB);
            return getBrandById(updatedBrand.getBrandId()).isPresent();
        }
        return false;
    }
}
