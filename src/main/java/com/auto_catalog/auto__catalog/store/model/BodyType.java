package com.auto_catalog.auto__catalog.store.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "body_types")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class BodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "body_type_id")
    private Long bodyTypeId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;


}
