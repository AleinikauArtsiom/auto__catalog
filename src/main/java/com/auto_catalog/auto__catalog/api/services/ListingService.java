package com.auto_catalog.auto__catalog.api.services;

import com.auto_catalog.auto__catalog.store.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ListingService {
    private final ListingRepository listingRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }
}
