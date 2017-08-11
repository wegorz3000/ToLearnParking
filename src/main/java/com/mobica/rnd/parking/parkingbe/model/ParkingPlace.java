package com.mobica.rnd.parking.parkingbe.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "parking_places")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ParkingPlace {
    @Id
    private String parkingPlaceId;
    @DBRef
    @NotNull
    private Parking parking;
    @Size(max = 20)
    @NotNull
    @NotEmpty
    private String number;
    @Size(max = 20)
    private String level;
    @Size(max = 100)
    private String location;
    private MaxCarWeight maxCarWeight;
    private Integer maxCarLength;
    private Integer maxCarWidth;


    public ParkingPlace(Parking parking, String number, String level, String location, MaxCarWeight maxCarWeight, Integer maxCarLength, Integer maxCarWidth) {
        this.parking = parking;
        this.number = number;
        this.level = level;
        this.location = location;
        this.maxCarWeight = maxCarWeight;
        if (maxCarLength > 0) this.maxCarLength = maxCarLength;
        if (maxCarWidth > 0) this.maxCarWidth = maxCarWidth;
    }

    public ParkingPlace(Parking parking, String number) {
        this.parking = parking;
        this.number = number;
    }
}
