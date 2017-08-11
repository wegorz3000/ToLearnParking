package com.mobica.rnd.parking.parkingbe.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mobica.rnd.parking.parkingbe.deserializer.BooleanDeserializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "cars")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class Car {
    @Id
    private String id;
    @DBRef
    private User owner;
    @Size(min = 1, max = 100)
    @NotNull
    private String brand;
    @NotNull
    @Size(min = 1, max = 100)
    private String model;
    @NotNull
    @Size(min = 1, max = 100)
    private String color;
    @NotNull
    @Size(min = 1, max = 100)
    private String plateNumber;
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean activeState;

    public Car(String id) {
        this.id = id;
    }

    public Car(String brand, String model, String color, String plateNumber, Boolean activeState) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.plateNumber = plateNumber;
        this.activeState = activeState;
    }

    public Car(User owner, String brand, String model, String color, String plateNumber, Boolean activeState) {
        this.owner = owner;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.plateNumber = plateNumber;
        this.activeState = activeState;
    }

    public Car(String brand, String model, String color, String plateNumber) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.plateNumber = plateNumber;
    }
}
