package com.auto_catalog.auto__catalog.api.controllers;

import com.auto_catalog.auto__catalog.api.dto.ModelCarDto;
import com.auto_catalog.auto__catalog.api.services.ModelCarService;
import com.auto_catalog.auto__catalog.store.entity.ModelCar;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@Transactional
@RestController
@RequestMapping("/api/v1/ModelCar")
public class ModelCarController {

    private final ModelCarService modelCarService;

    @Autowired
    public ModelCarController(ModelCarService modelCarService) {
        this.modelCarService = modelCarService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<ModelCarDto>> getAllModelCars() {
        List<ModelCarDto> modelCars = modelCarService.getAllModelCars();
        return ResponseEntity.ok(modelCars);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ModelCarDto> getModelCarById(@PathVariable Long id) {
        ModelCarDto modelCar = modelCarService.getModelCarById(id);
        if (modelCar != null) {
            return ResponseEntity.ok(modelCar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public ResponseEntity<ModelCarDto> createModelCar(@Valid @RequestBody ModelCarDto modelCarDto) {
        ModelCarDto createdModelCar = modelCarService.createModelCar(modelCarDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdModelCar);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModelCarById(@PathVariable Long id) {
        modelCarService.deleteModelCarById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateModelCar(@PathVariable Long id, @RequestBody ModelCarDto modelCarDto) {
        return new ResponseEntity<>(modelCarService.updateModelCar(modelCarDto) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

}
