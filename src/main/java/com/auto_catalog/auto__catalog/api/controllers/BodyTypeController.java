package com.auto_catalog.auto__catalog.api.controllers;

import com.auto_catalog.auto__catalog.api.dto.BodyTypeDto;
import com.auto_catalog.auto__catalog.api.services.BodyTypeService;
import com.auto_catalog.auto__catalog.store.entity.BodyType;
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

@RequestMapping("/api/v1/bodyType")
@RestController
public class BodyTypeController {
    private final BodyTypeService bodyTypeService;

    @Autowired
    public BodyTypeController(BodyTypeService bodyTypeService) {
        this.bodyTypeService = bodyTypeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodyType> getBodyTypeById(@PathVariable("id") Long id) {
        Optional<BodyType> bodyTypeOptional = bodyTypeService.getBodyTypeById(id);
        if (bodyTypeOptional.isPresent()) {
            return new ResponseEntity<>(bodyTypeOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping()
    public ResponseEntity<List<BodyType>> getAllBodyType() {
        return new ResponseEntity<>(bodyTypeService.getAllBodyType(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBodyTypeById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(bodyTypeService.deleteBodyTypeById(id) ? HttpStatus.NO_CONTENT :
                HttpStatus.CONFLICT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBodyType(@RequestBody BodyType bodyType, @PathVariable Long id) {
        return new ResponseEntity<>(bodyTypeService.updateBodyType(bodyType) ? HttpStatus.NO_CONTENT :
                HttpStatus.CONFLICT);
    }

     @PostMapping()
     public ResponseEntity<HttpStatus> createBodyType(@RequestBody @Valid BodyTypeDto bodyTypeDto){
         return new ResponseEntity<>(bodyTypeService.createBodyType(bodyTypeDto)? HttpStatus.CREATED :
                 HttpStatus.CONFLICT);
     }

}