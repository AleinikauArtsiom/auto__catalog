package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.BodyTypeDto;
import com.auto_catalog.auto__catalog.store.entity.BodyType;
import com.auto_catalog.auto__catalog.store.repository.BodyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BodyTypeService {
    private final BodyTypeRepository bodyTypeRepository;

    @Autowired
    public BodyTypeService(BodyTypeRepository bodyTypeRepository) {
        this.bodyTypeRepository = bodyTypeRepository;
    }

    public List<BodyType> getAllModel() {
        return bodyTypeRepository.findAll();

    }

    public boolean deleteBodyTypeById(Long id) {
        bodyTypeRepository.deleteById(id);
        return getBodyTypeById(id).isEmpty();
    }

    //ниже проверочка на то... существует ли заданный айди для удаления
    public boolean isUserExists(Long id) {
        Optional<BodyType> bodyTypeOptional = bodyTypeRepository.findById(id);
        return bodyTypeOptional.isPresent();
    }

    public Optional<BodyType> getBodyTypeById(Long id) {
         return bodyTypeRepository.findById(id);

    }

    public boolean createBodyType(BodyTypeDto bodyTypeDto) {
        BodyType bodyType = new BodyType();
        bodyType.setName(bodyTypeDto.getName());
        BodyType createdUser = bodyTypeRepository.save(bodyType);
        return getBodyTypeById(createdUser.getBodyTypeId()).isPresent();
    }

//    public boolean updateBodyType() {
//
//    }

}
