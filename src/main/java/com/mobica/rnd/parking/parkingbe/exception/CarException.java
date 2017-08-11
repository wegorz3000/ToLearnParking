package com.mobica.rnd.parking.parkingbe.exception;

import com.mobica.rnd.parking.parkingbe.model.CarExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarException extends Exception {
    private CarExceptionCode code;
}
