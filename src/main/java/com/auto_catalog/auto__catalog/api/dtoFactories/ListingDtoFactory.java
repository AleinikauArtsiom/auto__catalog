package com.auto_catalog.auto__catalog.api.dtoFactories;

import com.auto_catalog.auto__catalog.api.dto.ListingDto;
import com.auto_catalog.auto__catalog.store.entity.Listing;
import org.springframework.stereotype.Component;

@Component
public class ListingDtoFactory {
    public ListingDto makeListingDto(Listing listing) {
        return ListingDto.builder()
                .bodyName(listing.getBodyName())
                .modelName(listing.getModelName())
                .brandName(listing.getBrandName())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .createdAt(listing.getCreatedAt())
                .updatedAt(listing.getUpdatedAt())
                .build();
    }
}
