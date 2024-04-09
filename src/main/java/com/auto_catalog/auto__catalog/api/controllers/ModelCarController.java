package com.auto_catalog.auto__catalog.api.controllers;

import com.auto_catalog.auto__catalog.api.services.ModelCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller

public class ModelCarController {
    private final ModelCarService modelCarService;

    @Autowired
    public ModelCarController(ModelCarService modelCarService) {
        this.modelCarService = modelCarService;
    }




}
