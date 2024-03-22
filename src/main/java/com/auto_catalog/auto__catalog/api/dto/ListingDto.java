package com.auto_catalog.auto__catalog.api.dto;

import com.auto_catalog.auto__catalog.store.model.Car;
import com.auto_catalog.auto__catalog.store.model.User;
import lombok.*;

import java.security.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListingDto {

    private Long listingId;

    private String bodyName;

    private String modelName;

    private String brandName;

    private User user;

    private Car car;

    private String title;

    private String description;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String status;

}
