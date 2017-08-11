package com.mobica.rnd.parking.parkingbe.validator;


import com.mobica.rnd.parking.parkingbe.annotation.InMonthsLimit;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class DateInMonthsLimitValidator implements ConstraintValidator<InMonthsLimit, Optional<LocalDate>> {
    private int months;
    @Override
    public void initialize(InMonthsLimit inMonthsLimit) {
        months = inMonthsLimit.value();
    }

    @Override
    public boolean isValid(Optional<LocalDate> localDate, ConstraintValidatorContext constraintValidatorContext) {
        if(localDate.isPresent() && !localDate.get().isAfter(LocalDate.now().plusMonths(months))) {
            return true;
        }
        else {
            String template = "Date may not be null or after " + months + " month(s) from now";
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(template).addConstraintViolation();
            return false;
        }
    }
}
