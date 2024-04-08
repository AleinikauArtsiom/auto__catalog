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

@Controller
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }
    private final static String UPDATE_BRAND = "/api/update/brand/{id}";
    private final static String FIND_ID_BRAND = "/api/find/brand/{id}";
    private final static String CREATE_BRAND = "/api/create/brand";
    private final static String DELETE_BRAND_BY_ID = "/api/delete/brand/{id}";
    private final static String FIND_ALL_BRAND = "/api/find/all/brand";

    @GetMapping(FIND_ID_BRAND)
    public ResponseEntity<Brand> getBrandById(@PathVariable("id") Long id) {
        Optional<Brand> brandOptional = brandService.getBrandById(id);
        if (brandOptional.isPresent()) {
            return new ResponseEntity<>(brandOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    @GetMapping(FIND_ALL_BRAND)
    public ResponseEntity<List<Brand>> getAllBrand() {
        return new ResponseEntity<>(brandService.getAllBrand(), HttpStatus.OK);

    }
    @DeleteMapping(DELETE_BRAND_BY_ID)
    public ResponseEntity<HttpStatus> deleteBrandById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(brandService.deleteBrandById(id) ? HttpStatus.NO_CONTENT :
                HttpStatus.CONFLICT);


    }

    @PutMapping(UPDATE_BRAND)
    public ResponseEntity<HttpStatus> updateBrand(@RequestBody Brand brand) {
        return new ResponseEntity<>(brandService.updateBrand(brand) ? HttpStatus.NO_CONTENT :
                HttpStatus.CONFLICT);


    }
    @PostMapping(CREATE_BRAND)
    public String createBrand(@ModelAttribute @Valid BrandDto brandDto, ModelMap modelMap, BindingResult bindingResult, HttpServletResponse response) {
        modelMap.addAttribute("BrandDto", brandDto);
        modelMap.addAttribute("errors", bindingResult.getAllErrors());
        if (brandService.createBrand(brandDto)) {
            response.setStatus(201);
            return "success";

        } else {
            response.setStatus(409);
            return "failure";
        }
    }

}