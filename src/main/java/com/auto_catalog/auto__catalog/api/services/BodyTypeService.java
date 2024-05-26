package com.auto_catalog.auto__catalog.api.services;
import com.auto_catalog.auto__catalog.api.dto.BodyTypeDto;
import com.auto_catalog.auto__catalog.store.entity.BodyType;
import com.auto_catalog.auto__catalog.store.repository.BodyTypeRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BodyTypeService {
    private final BodyTypeRepository bodyTypeRepository;

    @Autowired
    public BodyTypeService(BodyTypeRepository bodyTypeRepository) {
        this.bodyTypeRepository = bodyTypeRepository;
    }

    public List<BodyType> getAllBodyType() {
        return bodyTypeRepository.findAll();

    }

    public boolean deleteBodyTypeById(Long id) {
        bodyTypeRepository.deleteById(id);
        return getBodyTypeById(id).isEmpty();
    }

    public Optional<BodyType> getBodyTypeById(Long id) {
        return bodyTypeRepository.findById(id);

    }

    public boolean createBodyType(@Valid BodyTypeDto bodyTypeDto) {
        BodyType bodyType = new BodyType();
        bodyType.setName(bodyTypeDto.getName());
        BodyType createdBodyType = bodyTypeRepository.save(bodyType);
        return getBodyTypeById(createdBodyType.getBodyTypeId()).isPresent();
    }

    public boolean updateBodyType(BodyType bodyType) {
        Optional<BodyType> bodyTypeFromDBOptional = (bodyTypeRepository.findById(bodyType.getBodyTypeId()));
        if (bodyTypeFromDBOptional.isPresent()) {
            BodyType bodyTyperFromDB = bodyTypeFromDBOptional.get();
            if (bodyType.getName() != null) {
                bodyTyperFromDB.setName(bodyType.getName());
            }
            BodyType updatedBodyType = bodyTypeRepository.save(bodyTyperFromDB);
            return getBodyTypeById(updatedBodyType.getBodyTypeId()).isPresent();
        }
        return false;
    }

    @PostConstruct
    public void populateBodyTypes() {
        List<String> bodyTypeNames = Arrays.asList(
                "Седан", "Хэтчбек", "Внедорожник", "Купе", "Кабриолет", "Универсал", "Фургон",
                "Пикап", "Минивэн", "Кроссовер", "Родстер", "Лимузин", "Тарга", "Фаэтон", "Багги",
                "Броневик", "Микроавтобус", "Мусоровоз", "Автобус", "Кабриолет с жесткой крышей",
                "Кабриолет с мягкой крышей", "Шаттл", "Спортивный автомобиль", "Трак",
                "Туристический автобус", "Школьный автобус", "Самосвал", "Грузовик", "Эвакуатор",
                "Скорая помощь", "Полицейская машина", "Такси", "Мотоцикл", "Скутер", "Квадроцикл"
        );

        for (String name : bodyTypeNames) {
            if (!bodyTypeRepository.findByName(name).isPresent()) {
                BodyType bodyType = new BodyType();
                bodyType.setName(name);
                bodyTypeRepository.save(bodyType);
            }
        }
    }
}