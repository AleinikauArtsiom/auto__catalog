package com.auto_catalog.auto__catalog.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private ModelCar modelCar;

    @ManyToOne
    @JoinColumn(name = "body_type_id")
    private BodyType bodyType;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "condition", nullable = false)
    private String condition;

}
