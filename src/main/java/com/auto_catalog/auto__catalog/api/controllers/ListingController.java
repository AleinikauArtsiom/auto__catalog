package com.auto_catalog.auto__catalog.api.controllers;

import com.auto_catalog.auto__catalog.api.dto.ListingDto;
import com.auto_catalog.auto__catalog.api.services.ListingService;
import com.auto_catalog.auto__catalog.store.entity.Listing;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController {

    private final ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> listings = listingService.getAllListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable Long id) {
        Listing listing = listingService.getListingById(id);
        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ListingDto> createListing(@Valid @RequestBody ListingDto listingDto) {
        ListingDto createdListingDto = listingService.createListing(listingDto);
        return new ResponseEntity<>(createdListingDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateListing(@PathVariable Long id,  @RequestBody Listing listing) {
        return new ResponseEntity<>(listingService.updateListing(listing) ? HttpStatus.NO_CONTENT :
                HttpStatus.CONFLICT);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteListingById(@PathVariable Long id) {
        listingService.deleteListingById(id);
        return new ResponseEntity<>("Listing deleted successfully", HttpStatus.OK);
    }
}
