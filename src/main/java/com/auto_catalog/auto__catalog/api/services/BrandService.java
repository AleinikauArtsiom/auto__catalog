package com.auto_catalog.auto__catalog.api.services;
import com.auto_catalog.auto__catalog.api.dto.BrandDto;
import com.auto_catalog.auto__catalog.api.dtoFactories.BrandDtoFactory;
import com.auto_catalog.auto__catalog.api.exception.NotFoundException;
import com.auto_catalog.auto__catalog.store.entity.Brand;
import com.auto_catalog.auto__catalog.store.repository.BrandRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final BrandDtoFactory brandDtoFactory;

    @Autowired
    public BrandService(BrandRepository brandRepository, BrandDtoFactory brandDtoFactory) {
        this.brandRepository = brandRepository;
        this.brandDtoFactory = brandDtoFactory;
    }
    public List<Brand> getAllBrand() {
        return brandRepository.findAll();

    }
    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name);
    }

    public void deleteBrandById(Long id) {
        getBrandOrThrowException(id);
        brandRepository.deleteById(id);
    }
    private Brand getBrandOrThrowException(Long id) {
        return brandRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with " + id + " doesn't exist"));
    }

    public Brand getBrandById(Long id) {
        return getBrandOrThrowException(id);
    }

    public BrandDto createBrand(@Valid BrandDto brandDto) {
        Brand brand = brandRepository.saveAndFlush(
              Brand.builder()
                .name(brandDto.getName()).build()
        );
        return brandDtoFactory.makeBrandFactory(brand);
    }

    public boolean updateBrand(Brand brand) {
        Optional<Brand> brandFromDBOptional = brandRepository.findById(brand.getBrandId());
        if (brandFromDBOptional.isPresent()) {
            Brand brandFromDB = brandFromDBOptional.get();
            if (brand.getName() != null) {
                brandFromDB.setName(brand.getName());
            }
            Brand updatedBrand = brandRepository.save(brandFromDB);
            return updatedBrand != null; // Возвращаем true, если бренд успешно обновлен, иначе false
        }
        return false;
    }
    @PostConstruct
    public void populateBrands() {
        List<String> brandNames = Arrays.asList(
                "АвтоВАЗ", "Audi", "BMW", "Chevrolet", "Chrysler", "Citroën", "Dodge",
                "Ferrari", "Fiat", "Ford", "Geely", "Honda", "Hyundai", "Jaguar", "Jeep",
                "Kia", "Lamborghini", "Land Rover", "Lexus", "Maserati", "Mazda",
                "Mercedes-Benz", "Mitsubishi", "Nissan", "Opel", "Peugeot", "Porsche",
                "Renault", "Rolls-Royce", "Saab", "Skoda", "Subaru", "Suzuki", "Tesla",
                "Toyota", "Volkswagen", "Volvo"
        );

        for (String name : brandNames) {
            if (!brandRepository.findByName(name).isPresent()) {
                Brand brand = new Brand();
                brand.setName(name);
                brandRepository.save(brand);
            }
        }
    }
}