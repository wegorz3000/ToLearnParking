package com.mobica.rnd.parking.parkingbe.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DateNotInPastValidatorTest {
    @Autowired
    private DateNotInPastValidator validator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void nowDateValidationTest() {
        assertTrue(validator.isValid(Optional.of(LocalDate.now()), constraintValidatorContext));
    }

    @Test
    public void futureDateValidationTest() {
        assertTrue(validator.isValid(Optional.of(LocalDate.now().plusDays(3)), constraintValidatorContext));
    }

    @Test
    public void pastDateValidationTest() {
        assertFalse(validator.isValid(Optional.of(LocalDate.now().minusDays(3)), constraintValidatorContext));
    }
}
