package com.auto_catalog.auto__catalog.api.controllers;
import com.auto_catalog.auto__catalog.api.dto.BrandDto;
import com.auto_catalog.auto__catalog.api.services.BrandService;
import com.auto_catalog.auto__catalog.store.entity.Brand;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable("id") Long id) {
       Brand brand = brandService.getBrandById(id);
       return ResponseEntity.ok(brand);
    }
    @GetMapping()
    public ResponseEntity<List<Brand>> getAllBrand() {
       List<Brand> brand = brandService.getAllBrand();
       return ResponseEntity.ok(brand);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBrandById(@PathVariable("id") Long id) {
        brandService.deleteBrandById(id);
      return ResponseEntity.noContent().build();

    }
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBrand(@RequestBody Brand brand, @PathVariable Long id) {
        return new ResponseEntity<>(brandService.updateBrand(brand) ? HttpStatus.NO_CONTENT :
                HttpStatus.CONFLICT);
    }
    @PostMapping()
    public ResponseEntity<BrandDto> createBrand(@Valid @RequestBody BrandDto brandDto) {
        BrandDto createBrandDto = brandService.createBrand(brandDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createBrandDto);
    }
}