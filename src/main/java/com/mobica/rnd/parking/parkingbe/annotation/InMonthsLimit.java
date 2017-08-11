package com.mobica.rnd.parking.parkingbe.annotation;

import com.mobica.rnd.parking.parkingbe.validator.DateInMonthsLimitValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = DateInMonthsLimitValidator.class)
@Documented
public @interface InMonthsLimit {
    String message() default "{msg.validation.date.inmonthslimit}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value() default 1;
}
