package com.auto_catalog.auto__catalog.model;
import ch.qos.logback.core.model.Model;
import jakarta.persistence.*;
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
    public Car(){

    }

    public Car(Long carId, ModelCar modelCar, BodyType bodyType, Integer year,
               Integer mileage, Integer price, String condition) {
        this.carId = carId;
        this.modelCar = modelCar;
        this.bodyType = bodyType;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        this.condition = condition;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public ModelCar getModel() {
        return modelCar;
    }

    public void setModel(ModelCar modelCar) {
        this.modelCar = modelCar;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
