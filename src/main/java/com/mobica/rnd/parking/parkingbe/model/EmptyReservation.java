package com.mobica.rnd.parking.parkingbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "empty_reservations")
public class EmptyReservation {
    @Id
    private String id;
    @DBRef
    private ParkingPlace parkingPlace;
    private LocalDate date;

    public EmptyReservation(ParkingPlace parkingPlace, LocalDate date) {
        this.parkingPlace = parkingPlace;
        this.date = date;
    }
}