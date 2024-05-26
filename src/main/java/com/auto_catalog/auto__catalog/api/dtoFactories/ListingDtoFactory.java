package com.auto_catalog.auto__catalog.api.dtoFactories;

import com.auto_catalog.auto__catalog.api.dto.ListingDto;
import com.auto_catalog.auto__catalog.store.entity.Listing;
import org.springframework.stereotype.Component;

@Component
public class ListingDtoFactory {
    public ListingDto makeListingDto(Listing listing) {
        return ListingDto.builder()
                .listingId(listing.getListingId())
                .bodyName(listing.getBodyName())
                .modelName(listing.getModelName())
                .brandName(listing.getBrandName())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .year(listing.getCar().getYear())
                .mileage(listing.getCar().getMileage())
                .price(listing.getCar().getPrice())
                .condition(listing.getCar().getCondition())
                .createdAt(listing.getCreatedAt())
                .updatedAt(listing.getUpdatedAt())
                .status(listing.getStatus())
                .build();
    }
}
