package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.ListingDto;
import com.auto_catalog.auto__catalog.api.dtoFactories.ListingDtoFactory;
import com.auto_catalog.auto__catalog.api.exception.NotFoundException;
import com.auto_catalog.auto__catalog.store.entity.*;
import com.auto_catalog.auto__catalog.store.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingDtoFactory listingDtoFactory;
    private final BodyTypeRepository bodyTypeRepository;
    private final BrandRepository brandRepository;
    private final ModelCarRepository modelCarRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository, ListingDtoFactory listingDtoFactory,
                          BodyTypeRepository bodyTypeRepository, BrandRepository brandRepository,
                          ModelCarRepository modelCarRepository, CarRepository carRepository,
                          UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.listingDtoFactory = listingDtoFactory;
        this.bodyTypeRepository = bodyTypeRepository;
        this.brandRepository = brandRepository;
        this.modelCarRepository = modelCarRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Listing getListingById(Long id) {
        return getListingOrThrowException(id);
    }

    public void deleteListingById(Long id) {
        Listing listing = getListingOrThrowException(id);
        User user = listing.getUser();
        user.getListings().remove(listing);
        user.decrementListingCount();
        listingRepository.deleteById(id);
        userRepository.save(user);
    }

    private Listing getListingOrThrowException(Long id) {
        return listingRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Listing with ID " + id + " not found"));
    }

    public ListingDto createListing(@Valid ListingDto listingDto, Principal principal) {
        // Fetch or create the necessary entities
        BodyType bodyType = bodyTypeRepository.findByName(listingDto.getBodyName())
                .orElseGet(() -> bodyTypeRepository.save(new BodyType(null, listingDto.getBodyName())));

        Brand brand = brandRepository.findByName(listingDto.getBrandName())
                .orElseGet(() -> brandRepository.save(new Brand(null, listingDto.getBrandName())));

        ModelCar modelCar = modelCarRepository.findByName(listingDto.getModelName())
                .orElseGet(() -> modelCarRepository.save(new ModelCar(null, brand, listingDto.getModelName())));

        Car car = carRepository.save(new Car(null, modelCar, bodyType, listingDto.getYear(),
                listingDto.getMileage(), listingDto.getPrice(), listingDto.getCondition()));

        // Fetch the user
        User user = userRepository.findByUserLogin(principal.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Listing listing = Listing.builder()
                .bodyName(listingDto.getBodyName())
                .modelName(listingDto.getModelName())
                .brandName(listingDto.getBrandName())
                .title(listingDto.getTitle())
                .description(listingDto.getDescription())
                .car(car)
                .user(user)
                .status(listingDto.getStatus())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        user.getListings().add(listing);
        user.incrementListingCount();
        listingRepository.save(listing);
        userRepository.save(user);

        return listingDtoFactory.makeListingDto(listing);
    }


    public boolean updateListing(ListingDto listingDto) {
        Optional<Listing> listingFromDBOptional = listingRepository.findById(listingDto.getListingId());
        if (listingFromDBOptional.isPresent()) {
            Listing listingFromDB = listingFromDBOptional.get();

            if (listingDto.getBodyName() != null) {
                listingFromDB.setBodyName(listingDto.getBodyName());
            }
            if (listingDto.getYear() != null) {
                listingFromDB.getCar().setYear(listingDto.getYear());
            }
            if (listingDto.getMileage() != null) {
                listingFromDB.getCar().setMileage(listingDto.getMileage());
            }
            if (listingDto.getPrice() != null) {
                listingFromDB.getCar().setPrice(listingDto.getPrice());
            }
            if (listingDto.getCondition() != null) {
                listingFromDB.getCar().setCondition(listingDto.getCondition());
            }
            if (listingDto.getModelName() != null) {
                listingFromDB.setModelName(listingDto.getModelName());
            }
            if (listingDto.getBrandName() != null) {
                listingFromDB.setBrandName(listingDto.getBrandName());
            }
            if (listingDto.getTitle() != null) {
                listingFromDB.setTitle(listingDto.getTitle());
            }
            if (listingDto.getDescription() != null) {
                listingFromDB.setDescription(listingDto.getDescription());
            }
            if (listingDto.getStatus() != null) {
                listingFromDB.setStatus(listingDto.getStatus());
            }

            listingFromDB.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            Listing updatedListing = listingRepository.saveAndFlush(listingFromDB);

            return listingFromDB.equals(updatedListing);
        } else {
            throw new NotFoundException("Listing with ID " + listingDto.getListingId() + " not found");
        }
    }
}
