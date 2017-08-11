package com.mobica.rnd.parking.parkingbe.exception;


import com.mobica.rnd.parking.parkingbe.model.MarkAvailableSlotsExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MarkAvailableSlotsException extends Exception {
    private MarkAvailableSlotsExceptionCode code;
}
