package com.auto_catalog.auto__catalog.api.controllers;

import com.auto_catalog.auto__catalog.api.dto.ListingDto;
import com.auto_catalog.auto__catalog.api.services.ListingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@Transactional
@RestController
@RequestMapping("/listings")
public class ListingController {

    private final ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<ListingDto>> getAllListings() {
        List<ListingDto> listings = listingService.getAllListings();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ListingDto> getListingById(@PathVariable Long id) {
        ListingDto listing = listingService.getListingById(id);
        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<ListingDto> createListing(@Valid @RequestBody ListingDto listingDto, Principal principal) {
        ListingDto createdListingDto = listingService.createListing(listingDto, principal);
        return new ResponseEntity<>(createdListingDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateListing(@PathVariable Long id, @RequestBody ListingDto listingDto, Principal principal) {
        listingDto.setListingId(id);
        return new ResponseEntity<>(listingService.updateListing(listingDto, principal) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}")
    public ResponseEntity<HttpStatus> updateListingForAdmin(@PathVariable Long id, @RequestBody ListingDto listingDto) {
        listingDto.setListingId(id);
        return new ResponseEntity<>(listingService.updateListingForAdmin(listingDto) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/my-listings")
    public ResponseEntity<List<ListingDto>> getMyListings(Principal principal) {
        List<ListingDto> listings = listingService.getListingsByCurrentUser(principal.getName());
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteListingById(@PathVariable Long id, Principal principal) {
        listingService.deleteListingById(id, principal);
        return new ResponseEntity<>("Listing deleted successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteListingByIdForAdmin(@PathVariable Long id) {
        listingService.deleteListingByIdForAdmin(id);
        return new ResponseEntity<>("Listing deleted successfully", HttpStatus.OK);
    }
}
