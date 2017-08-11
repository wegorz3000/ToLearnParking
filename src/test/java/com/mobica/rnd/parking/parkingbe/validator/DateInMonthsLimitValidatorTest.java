package com.mobica.rnd.parking.parkingbe.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateInMonthsLimitValidatorTest {
    @Autowired
    private DateInMonthsLimitValidator validator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Before
    public void setup() {
        Mockito.doNothing().when(constraintValidatorContext).disableDefaultConstraintViolation();
        Mockito.doReturn(constraintValidatorContext).when(constraintViolationBuilder).addConstraintViolation();
        Mockito.doReturn(constraintViolationBuilder).when(constraintValidatorContext).buildConstraintViolationWithTemplate(any(String.class));
    }

    @Test
    public void nowDateValidationTest() {
        ReflectionTestUtils.setField(validator, "months", 1);
        assertTrue(validator.isValid(Optional.of(LocalDate.now()), constraintValidatorContext));
    }
    @Test
    public void monthLaterValidationTest() {
        ReflectionTestUtils.setField(validator, "months", 1);
        assertTrue(validator.isValid(Optional.of(LocalDate.now().plusMonths(1)), constraintValidatorContext));
    }
    @Test
    public void monthAndDayLaterValidationTest() {
        ReflectionTestUtils.setField(validator, "months", 1);
        assertFalse(validator.isValid(Optional.of(LocalDate.now().plusMonths(1).plusDays(1)), constraintValidatorContext));
    }
    @Test
    public void twentyEightDaysLaterValidationTest() {
        ReflectionTestUtils.setField(validator, "months", 1);
        assertTrue(validator.isValid(Optional.of(LocalDate.now().plusDays(28)), constraintValidatorContext));
    }
}
