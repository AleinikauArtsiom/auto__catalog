package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.api.dto.ListingDto;
import com.auto_catalog.auto__catalog.api.dtoFactories.ListingDtoFactory;
import com.auto_catalog.auto__catalog.api.exception.NotFoundException;
import com.auto_catalog.auto__catalog.store.entity.Listing;
import com.auto_catalog.auto__catalog.store.repository.ListingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingDtoFactory listingDtoFactory;

    @Autowired
    public ListingService(ListingRepository listingRepository, ListingDtoFactory listingDtoFactory) {
        this.listingRepository = listingRepository;
        this.listingDtoFactory = listingDtoFactory;
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Listing getListingById(Long id) {
        return getListingOrThrowException(id);
    }

    public void deleteListingById(Long id) {
        getListingOrThrowException(id);
        listingRepository.deleteById(id);
    }

    private Listing getListingOrThrowException(Long id) {
        return listingRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Listing with ID " + id + " not found"));
    }

    public ListingDto createListing (@Valid ListingDto listingDto) {
        Listing listing = Listing.builder()
                .bodyName(listingDto.getBodyName())
                .modelName(listingDto.getModelName())
                .brandName(listingDto.getBrandName())
                .title(listingDto.getTitle())
                .description(listingDto.getDescription())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now())).build
               ();
        return listingDtoFactory.makeListingDto(listing);
    }


    public boolean updateListing(Listing listing) {
        Optional<Listing> listingFromDBOptional = listingRepository.findById(listing.getListingId());
        if (listingFromDBOptional.isPresent()) {
            Listing listingFromDB = listingFromDBOptional.get();
            if (listing.getBodyName() != null) {
                listingFromDB.setBodyName(listing.getBodyName());
            }
            if (listing.getModelName() != null) {
                listingFromDB.setModelName(listing.getModelName());
            }
            if (listing.getBrandName() != null) {
                listingFromDB.setBrandName(listing.getBrandName());
            }
            if (listing.getTitle() != null) {
                listingFromDB.setTitle(listing.getTitle());
            }
            if (listing.getDescription() != null) {
                listingFromDB.setDescription(listing.getDescription());
            }
            listingFromDB.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            Listing updatedListing = listingRepository.saveAndFlush(listingFromDB);

            return listingFromDB.equals(updatedListing);
        }
        return false;
    }
}
