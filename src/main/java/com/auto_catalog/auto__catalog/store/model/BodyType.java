package com.auto_catalog.auto__catalog.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Reference;

@Entity
@Table(name = "body_types", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})

public class BodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "body_type_id")
    private Long bodyTypeId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public BodyType(){

    }

    public BodyType(Long body_type_id, String name) {
        this.bodyTypeId = body_type_id;
        this.name = name;
    }

    public Long getBody_type_id() {
        return bodyTypeId;
    }

    public void setBody_type_id(Long body_type_id) {
        this.bodyTypeId = body_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
