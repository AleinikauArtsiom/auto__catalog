package com.auto_catalog.auto__catalog.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "body_types")
@Entity
public class BodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "body_type_id")
    private Long bodyTypeId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;


}
