package com.mobica.rnd.parking.parkingbe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MarkAvailableSlotsExceptionCode {
    PARKING_PLACE_NOT_FOUND("msg.validation.parkingplace.notfound"),
    PARKING_PLACE_BOOKED("msg.validation.parkingplace.booked"),
    ALREADY_AVAILABLE("msg.validation.parkingplace.alreadyavailable"),
    END_DATE_BEFORE_START("msg.validation.date.endbeforestart");

    private String descriptionProperty;
}
