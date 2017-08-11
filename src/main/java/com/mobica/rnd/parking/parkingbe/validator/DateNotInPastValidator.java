package com.mobica.rnd.parking.parkingbe.validator;

import com.mobica.rnd.parking.parkingbe.annotation.NotPast;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class DateNotInPastValidator implements ConstraintValidator<NotPast, Optional<LocalDate>> {

    @Override
    public void initialize(NotPast notPast) {

    }

    @Override
    public boolean isValid(Optional<LocalDate> date, ConstraintValidatorContext constraintValidatorContext) {
        if(date.isPresent()) {
            return !date.get().isBefore(LocalDate.now());
        }
        else {
            return false;
        }
    }
}
