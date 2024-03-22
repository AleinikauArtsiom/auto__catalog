package com.auto_catalog.auto__catalog.store.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

}

