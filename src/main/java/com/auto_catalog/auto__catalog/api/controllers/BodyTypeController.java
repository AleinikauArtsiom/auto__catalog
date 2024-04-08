package com.auto_catalog.auto__catalog.api.controllers;

import com.auto_catalog.auto__catalog.api.dto.BodyTypeDto;
import com.auto_catalog.auto__catalog.api.services.BodyTypeService;
import com.auto_catalog.auto__catalog.api.services.UserService;
import com.auto_catalog.auto__catalog.store.model.BodyType;
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
public class BodyTypeController {
    private final BodyTypeService bodyTypeService;

    private final static String UPDATE_BODY_TYPE = "/api/update/bodytype/{id}";
    private final static String FIND_ID_BODY_TYPE = "/api/find/bodytype/{id}";
    private final static String CREATE_BODY_TYPE = "/api/create/bodytype";
    private final static String DELETE_BODY_TYPE_BY_ID = "/api/delete/bodytype/{id}";
    private final static String FIND_ALL_BODY_TYPE = "/api/find/all/bodytype";

    @Autowired
    public BodyTypeController(BodyTypeService bodyTypeService) {
        this.bodyTypeService = bodyTypeService;
    }

    @GetMapping(FIND_ID_BODY_TYPE)

    public ResponseEntity<BodyType> getBodyTypeById(@PathVariable("id") Long id) {
        Optional<BodyType> bodyTypeOptional = bodyTypeService.getBodyTypeById(id);
        if (bodyTypeOptional.isPresent()) {
            return new ResponseEntity<>(bodyTypeOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping(FIND_ALL_BODY_TYPE)
    public ResponseEntity<List<BodyType>> getAllBodyType() {
        return new ResponseEntity<>(bodyTypeService.getAllBodyType(), HttpStatus.OK);

    }

    @DeleteMapping(DELETE_BODY_TYPE_BY_ID)
    public ResponseEntity<HttpStatus> deleteBodyTypeById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(bodyTypeService.deleteBodyTypeById(id) ? HttpStatus.NO_CONTENT :
                HttpStatus.CONFLICT);


    }

    @PutMapping(UPDATE_BODY_TYPE)
    public ResponseEntity<HttpStatus> updateBodyType(@RequestBody BodyType bodyType) {
        return new ResponseEntity<>(bodyTypeService.updateBodyType(bodyType) ? HttpStatus.NO_CONTENT :
                HttpStatus.CONFLICT);


    }

    /* @PostMapping(CREATE_BODY_TYPE)
     public ResponseEntity<HttpStatus> createBodyType(@RequestBody @Valid BodyTypeDto bodyTypeDto, BindingResult result){
         return new ResponseEntity<>(bodyTypeService.createBodyType(bodyTypeDto)? HttpStatus.CREATED :
                                                                                  HttpStatus.CONFLICT);
     }*/
    @PostMapping(CREATE_BODY_TYPE)
    public String createBodyType(@ModelAttribute @Valid BodyTypeDto bodyTypeDto, ModelMap modelMap, BindingResult bindingResult, HttpServletResponse response) {
        modelMap.addAttribute("userDto", bodyTypeDto);
        modelMap.addAttribute("errors", bindingResult.getAllErrors());
        if (bodyTypeService.createBodyType(bodyTypeDto)) {
            response.setStatus(201);
            return "success";

        } else {
            response.setStatus(409);
            return "failure";
        }
    }
}
