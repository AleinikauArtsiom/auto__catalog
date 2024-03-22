package com.auto_catalog.auto__catalog.api.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BodyTypeDto {
    private Long bodyTypeId;

    private String name;

}
