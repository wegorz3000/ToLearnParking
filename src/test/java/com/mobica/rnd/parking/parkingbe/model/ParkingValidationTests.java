package com.mobica.rnd.parking.parkingbe.model;

import com.mobica.rnd.parking.parkingbe.ParkingBeApplication;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ParkingBeApplication.class})
@TestPropertySource("classpath:ValidationMessages.properties")
public class ParkingValidationTests {

    private Validator validator;
    private Parking parking = new Parking(RandomStringUtils.randomAlphanumeric(10), ThreadLocalRandom.current().nextLong(100));
    private Set<ConstraintViolation<Parking>> violations;

    @Value("${msg.validation.parking.name.notempty}")
    private String parkingNameNotEmptyErrorMessage;
    @Value("${msg.validation.parking.name.size}")
    private String parkingNameSizeErrorMessage;
    @Value("${msg.validation.parking.capacity.notnull}")
    private String parkingCapacityNotNullErrorMessage;
    @Value("${msg.validation.parking.capacity.max}")
    private String parkingCapacityMaxErrorMessage;


    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }


    @Test
    public void parkingNameCorrectDataTest() {
        Set<ConstraintViolation<Parking>> violations = validator.validate(parking);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void parkingNameEmptyFieldTest() {
        parking.setName(StringUtils.EMPTY);
        violations = validator.validate(parking);
        Assert.assertEquals(parkingNameNotEmptyErrorMessage, violations.iterator().next().getMessage());
        parking.setName(RandomStringUtils.randomAlphanumeric(10));
    }

    @Test
    public void parkingNameTooMuchCharactersTest() {
        parking.setName(RandomStringUtils.randomAlphanumeric(101));
        violations = validator.validate(parking);
        Assert.assertEquals(parkingNameSizeErrorMessage, violations.iterator().next().getMessage());
        parking.setName(RandomStringUtils.randomAlphanumeric(10));

    }

    @Test
    public void parkingCapacityCorrectDataTests() {
        violations = validator.validate(parking);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void parkingCapacityNullFieldTests() {
        parking.setCapacity(null);
        violations = validator.validate(parking);
        Assert.assertEquals(parkingCapacityNotNullErrorMessage, violations.iterator().next().getMessage());
        parking.setCapacity(ThreadLocalRandom.current().nextLong(100));
    }

    @Test
    public void parkingCapacityTooBigNumberTests() {
        parking.setCapacity(ThreadLocalRandom.current().nextLong(100000,200000));
        violations = validator.validate(parking);
        Assert.assertEquals(parkingCapacityMaxErrorMessage, violations.iterator().next().getMessage());
        parking.setCapacity(ThreadLocalRandom.current().nextLong(100));

    }

}
