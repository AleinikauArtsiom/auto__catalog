package com.auto_catalog.auto__catalog.model;

import jakarta.persistence.*;


@Entity
@Table(name = "models", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class ModelCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private Long modelId;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

   public ModelCar(){

   }

    public ModelCar(Long modelId, String name, Brand brand) {
        this.modelId = modelId;
        this.name = name;
        this.brand = brand;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
