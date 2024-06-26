package com.auto_catalog.auto__catalog.api.dto;

import com.auto_catalog.auto__catalog.store.entity.Listing;
import com.auto_catalog.auto__catalog.store.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListingDto {

    private Long listingId;

    private String bodyName;

    private String modelName;

    private String brandName;

    private String title;

    private String description;

    private Integer year;

    private Integer mileage;

    private Integer price;

    private String condition;

    @JsonProperty(namespace = "created_at")
    private Timestamp createdAt;

    @JsonProperty(namespace = "updated_at")
    private Timestamp updatedAt;

    private String status;

}
