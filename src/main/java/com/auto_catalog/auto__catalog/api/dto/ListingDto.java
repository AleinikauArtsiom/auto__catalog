package com.auto_catalog.auto__catalog.api.dto;

import com.auto_catalog.auto__catalog.store.entity.Car;
import com.auto_catalog.auto__catalog.store.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(namespace = "created_at")
    private Timestamp createdAt;

    @JsonProperty(namespace = "updated_at")
    private Timestamp updatedAt;

    private String status;

}
