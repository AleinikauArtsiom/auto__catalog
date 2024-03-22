package com.auto_catalog.auto__catalog.model;
import jakarta.persistence.*;

@Entity
@Table(name = "brands", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")})

public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandid;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public Brand(){

    }

    public Brand(Long brand_id, String name) {
        this.brandid = brand_id;
        this.name = name;
    }

    public Long getBrand_id() {
        return brandid;
    }

    public void setBrand_id(Long brand_id) {
        this.brandid = brand_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

