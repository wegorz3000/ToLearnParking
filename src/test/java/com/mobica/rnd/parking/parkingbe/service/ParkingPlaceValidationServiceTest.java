package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.exception.ParkingNotFoundException;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ParkingPlaceValidationServiceTest {
    @Mock
    private ParkingPlaceValidationService parkingPlaceValidationService;

    @Mock
    private ParkingPlace parkingPlace;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = ParkingNotFoundException.class)
    public void negativeValidateUserTest() throws ParkingNotFoundException {
        Mockito.doThrow(ParkingNotFoundException.class).when(parkingPlaceValidationService).validateParkingPlace(parkingPlace);
        parkingPlaceValidationService.validateParkingPlace(parkingPlace);
    }

    @Test
    public void positiveValidateUserTest() throws ParkingNotFoundException {
        Mockito.doNothing().when(parkingPlaceValidationService).validateParkingPlace(parkingPlace);
        parkingPlaceValidationService.validateParkingPlace(parkingPlace);
        Mockito.verify(parkingPlaceValidationService, Mockito.times(1)).validateParkingPlace(parkingPlace);
    }
}
