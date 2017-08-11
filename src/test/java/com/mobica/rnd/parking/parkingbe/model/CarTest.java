package com.mobica.rnd.parking.parkingbe.model;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarTest {
    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    public void positiveValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                true);
        assertTrue(validator.validateProperty(car, "brand").isEmpty());
        assertTrue(validator.validateProperty(car, "model").isEmpty());
        assertTrue(validator.validateProperty(car, "color").isEmpty());
        assertTrue(validator.validateProperty(car, "plateNumber").isEmpty());
    }

    @Test
    public void emptyBrandValidationTest() {
        Car car = new Car(StringUtils.EMPTY,
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                true);
        assertFalse(validator.validateProperty(car, "brand").isEmpty());
    }

    @Test
    public void emptyModelValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(1),
                StringUtils.EMPTY,
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                true);
        assertFalse(validator.validateProperty(car, "model").isEmpty());
    }

    @Test
    public void emptyColorValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                StringUtils.EMPTY,
                RandomStringUtils.randomAlphanumeric(1),
                true);
        assertFalse(validator.validateProperty(car, "color").isEmpty());
    }

    @Test
    public void emptyPlateNumberValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                StringUtils.EMPTY,
                true);
        assertFalse(validator.validateProperty(car, "plateNumber").isEmpty());
    }

    @Test
    public void nullPlateNumberValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                null,
                true);
        assertFalse(validator.validateProperty(car, "plateNumber").isEmpty());
    }

    @Test
    public void nullModelValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(1),
                null,
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                true);
        assertFalse(validator.validateProperty(car, "model").isEmpty());
    }

    @Test
    public void nullBrandValidationTest() {
        Car car = new Car(null,
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                true);
        assertFalse(validator.validateProperty(car, "brand").isEmpty());
    }

    @Test
    public void nullColorValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                null,
                RandomStringUtils.randomAlphanumeric(1),
                true);
        assertFalse(validator.validateProperty(car, "color").isEmpty());
    }

    @Test
    public void overlengthBrandValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(101),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                true);
        assertFalse(validator.validateProperty(car, "brand").isEmpty());
    }

    @Test
    public void overlengthModelValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(101),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1), true);
        assertFalse(validator.validateProperty(car, "model").isEmpty());
    }

    @Test
    public void overlengthColorValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(101),
                RandomStringUtils.randomAlphanumeric(1),
                true);
        assertFalse(validator.validateProperty(car, "color").isEmpty());
    }

    @Test
    public void overlengthPlateNumberValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(101),
                true);
        assertFalse(validator.validateProperty(car, "plateNumber").isEmpty());
    }

    @Test
    public void allNullValidationTest() {
        Car car = new Car(null, null, null, null);
        Set<ConstraintViolation<Car>> constraintViolationSet = validator.validateProperty(car, "model");
        assertFalse(validator.validateProperty(car, "brand").isEmpty());
        assertFalse(validator.validateProperty(car, "model").isEmpty());
        assertFalse(validator.validateProperty(car, "color").isEmpty());
        assertFalse(validator.validateProperty(car, "plateNumber").isEmpty());
    }

    @Test
    public void allEmptyValidationTest() {
        Car car = new Car(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        assertFalse(validator.validateProperty(car, "brand").isEmpty());
        assertFalse(validator.validateProperty(car, "model").isEmpty());
        assertFalse(validator.validateProperty(car, "color").isEmpty());
        assertFalse(validator.validateProperty(car, "plateNumber").isEmpty());
    }

    @Test
    public void allOverlengthValidationTest() {
        Car car = new Car(RandomStringUtils.randomAlphanumeric(101),
                RandomStringUtils.randomAlphanumeric(101),
                RandomStringUtils.randomAlphanumeric(101),
                RandomStringUtils.randomAlphanumeric(101));
        assertFalse(validator.validateProperty(car, "brand").isEmpty());
        assertFalse(validator.validateProperty(car, "model").isEmpty());
        assertFalse(validator.validateProperty(car, "color").isEmpty());
        assertFalse(validator.validateProperty(car, "plateNumber").isEmpty());
    }
}
