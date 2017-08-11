package com.mobica.rnd.parking.parkingbe.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingPlaceTest {
    @Autowired
    private LocalValidatorFactoryBean validator;

    @Test
    public void positiveValidationTest() {
        ParkingPlace parkingPlace = new ParkingPlace(new Parking(RandomStringUtils.randomAlphanumeric(1)),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                MaxCarWeight.FROM_1_5_TO_2T,
                100,
                200);
        assertTrue(validator.validateProperty(parkingPlace, "number").isEmpty());
    }

    @Test
    public void emptyNumberValidationTest() {
        ParkingPlace parkingPlace = new ParkingPlace(new Parking(RandomStringUtils.randomAlphanumeric(1)),
                StringUtils.EMPTY,
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                MaxCarWeight.FROM_1_5_TO_2T,
                100,
                200);
        assertFalse(validator.validateProperty(parkingPlace, "number").isEmpty());
    }

    @Test
    public void nullNumberValidationTest() {
        ParkingPlace parkingPlace = new ParkingPlace(new Parking(RandomStringUtils.randomAlphanumeric(1)),
                null,
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                MaxCarWeight.FROM_1_5_TO_2T,
                100,
                200);
        assertFalse(validator.validateProperty(parkingPlace, "number").isEmpty());
    }

    @Test
    public void oversizedNumberValidationTest() {
        ParkingPlace parkingPlace = new ParkingPlace(new Parking(RandomStringUtils.randomAlphanumeric(1)),
                RandomStringUtils.randomAlphanumeric(21),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                MaxCarWeight.FROM_1_5_TO_2T,
                100,
                200);
        assertFalse(validator.validateProperty(parkingPlace, "number").isEmpty());
    }

    @Test
    public void oversizedLevelValidationTest() {
        ParkingPlace parkingPlace = new ParkingPlace(new Parking(RandomStringUtils.randomAlphanumeric(1)),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(21),
                RandomStringUtils.randomAlphanumeric(1),
                MaxCarWeight.FROM_1_5_TO_2T,
                100,
                200);
        assertFalse(validator.validateProperty(parkingPlace, "level").isEmpty());
    }

    @Test
    public void oversizedLocationValidationTest() {
        ParkingPlace parkingPlace = new ParkingPlace(new Parking(RandomStringUtils.randomAlphanumeric(1)),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(101),
                MaxCarWeight.FROM_1_5_TO_2T,
                100,
                200);
        assertFalse(validator.validateProperty(parkingPlace, "location").isEmpty());
    }
}
