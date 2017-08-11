package com.mobica.rnd.parking.parkingbe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CarExceptionCode {
    CAR_NOT_FOUND("msg.validation.car.cardoesntexist"),
    NULL_ACTIVE_STATE("msg.validation.car.nullactivestate");

    String descriptionProperty;
}
