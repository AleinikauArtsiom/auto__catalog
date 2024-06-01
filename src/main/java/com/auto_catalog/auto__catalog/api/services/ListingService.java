package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.ListingDto;
import com.auto_catalog.auto__catalog.api.dtoFactories.ListingDtoFactory;
import com.auto_catalog.auto__catalog.api.exception.NotFoundException;
import com.auto_catalog.auto__catalog.api.security.entity.UserSecurity;
import com.auto_catalog.auto__catalog.api.security.repository.UserSecurityRepository;
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
import java.util.stream.Collectors;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingDtoFactory listingDtoFactory;
    private final BodyTypeRepository bodyTypeRepository;
    private final BrandRepository brandRepository;
    private final ModelCarRepository modelCarRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final UserSecurityRepository userSecurityRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository, ListingDtoFactory listingDtoFactory,
                          BodyTypeRepository bodyTypeRepository, BrandRepository brandRepository,
                          ModelCarRepository modelCarRepository, CarRepository carRepository,
                          UserRepository userRepository, UserSecurityRepository userSecurityRepository) {
        this.listingRepository = listingRepository;
        this.listingDtoFactory = listingDtoFactory;
        this.bodyTypeRepository = bodyTypeRepository;
        this.brandRepository = brandRepository;
        this.modelCarRepository = modelCarRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.userSecurityRepository = userSecurityRepository;
    }

    public List<ListingDto> getListingsByCurrentUser(String userLogin) {
        Optional<User> userOptional = userSecurityRepository.findByUserLogin(userLogin)
                .map(UserSecurity::getUser);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User user = userOptional.get();
        List<Listing> listings = listingRepository.findByUser(user);
        if (listings.isEmpty()) {
            throw new NotFoundException("You have no active listings");
        }
        return listings.stream()
                .map(listingDtoFactory::makeListingDto)
                .collect(Collectors.toList());
    }

    public List<ListingDto> getAllListings() {
        return listingRepository.findAll()
                .stream()
                .map(listingDtoFactory::makeListingDto)
                .collect(Collectors.toList());
    }

    public ListingDto getListingById(Long id) {
        return getListingOrThrowException(id);
    }

    public void deleteListingById(Long id, Principal principal) {
        User user = userSecurityRepository.findByUserLogin(principal.getName())
                .map(UserSecurity::getUser)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Listing with ID " + id + " not found"));

        if (!listing.getUser().equals(user)) {
            throw new NotFoundException("Listing not found for current user");
        }

        user.getListings().remove(listing);
        user.decrementListingCount();
        listingRepository.deleteById(id);
        userRepository.save(user);
        carRepository.delete(listing.getCar());
    }

    public void deleteListingByIdForAdmin(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Listing with ID " + id + " not found"));

        User user = listing.getUser();
        user.getListings().remove(listing);
        user.decrementListingCount();
        listingRepository.deleteById(id);
        userRepository.save(user);
        carRepository.delete(listing.getCar());
    }

    private ListingDto getListingOrThrowException(Long id) {
        Listing listing = getListingOrThrowExceptionEntity(id);
        return listingDtoFactory.makeListingDto(listing);
    }

    private Listing getListingOrThrowExceptionEntity(Long id) {
        return listingRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Listing with ID " + id + " not found"));
    }

    public ListingDto createListing(@Valid ListingDto listingDto, Principal principal) {
        BodyType bodyType = bodyTypeRepository.findByName(listingDto.getBodyName())
                .orElseGet(() -> bodyTypeRepository.save(new BodyType(null, listingDto.getBodyName())));

        Brand brand = brandRepository.findByName(listingDto.getBrandName())
                .orElseGet(() -> brandRepository.save(new Brand(null, listingDto.getBrandName())));

        ModelCar modelCar = modelCarRepository.findByName(listingDto.getModelName())
                .orElseGet(() -> modelCarRepository.save(new ModelCar(null, brand, listingDto.getModelName())));

        Car car = carRepository.save(new Car(null, modelCar, bodyType, listingDto.getYear(),
                listingDto.getMileage(), listingDto.getPrice(), listingDto.getCondition()));

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

    public boolean updateListing(ListingDto listingDto, Principal principal) {
        User user = userSecurityRepository.findByUserLogin(principal.getName())
                .map(UserSecurity::getUser)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Listing listingFromDB = listingRepository.findById(listingDto.getListingId())
                .orElseThrow(() -> new NotFoundException("Listing with ID " + listingDto.getListingId() + " not found"));

        if (!listingFromDB.getUser().equals(user)) {
            throw new NotFoundException("Listing not found for current user");
        }

        updateListingDetails(listingFromDB, listingDto);

        Listing updatedListing = listingRepository.saveAndFlush(listingFromDB);
        return listingFromDB.equals(updatedListing);
    }

    public boolean updateListingForAdmin(ListingDto listingDto) {
        Listing listingFromDB = listingRepository.findById(listingDto.getListingId())
                .orElseThrow(() -> new NotFoundException("Listing with ID " + listingDto.getListingId() + " not found"));

        updateListingDetails(listingFromDB, listingDto);

        Listing updatedListing = listingRepository.saveAndFlush(listingFromDB);
        return listingFromDB.equals(updatedListing);
    }

    private void updateListingDetails(Listing listingFromDB, ListingDto listingDto) {
        if (listingDto.getBodyName() != null) {
            BodyType newBodyType = bodyTypeRepository.findByName(listingDto.getBodyName())
                    .orElseGet(() -> bodyTypeRepository.save(new BodyType(null, listingDto.getBodyName())));
            listingFromDB.getCar().setBodyType(newBodyType);
            listingFromDB.setBodyName(listingDto.getBodyName());
        }
        if (listingDto.getModelName() != null) {
            ModelCar newModelCar = modelCarRepository.findByName(listingDto.getModelName())
                    .orElseGet(() -> modelCarRepository.save(new ModelCar(null, listingFromDB.getCar().getModelCar().getBrand(), listingDto.getModelName())));
            listingFromDB.getCar().setModelCar(newModelCar);
            listingFromDB.setModelName(listingDto.getModelName());
        }
        if (listingDto.getBrandName() != null) {
            Brand newBrand = brandRepository.findByName(listingDto.getBrandName())
                    .orElseGet(() -> brandRepository.save(new Brand(null, listingDto.getBrandName())));
            listingFromDB.getCar().getModelCar().setBrand(newBrand);
            listingFromDB.setBrandName(listingDto.getBrandName());
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
    }
}