package com.mobica.rnd.parking.parkingbe.controller;

import com.mobica.rnd.parking.parkingbe.exception.ParkingNotFoundException;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import com.mobica.rnd.parking.parkingbe.service.ParkingPlaceManagementService;
import com.mobica.rnd.parking.parkingbe.service.ParkingPlaceValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ParkingPlaceManagementControllerTest {
    @InjectMocks
    private ParkingPlaceManagementController parkingPlaceManagementController;

    @Mock
    private ParkingPlaceManagementService parkingPlaceManagementService;

    @Mock
    private ParkingPlace parkingPlace;

    @Test
    public void addParkingPlaceTest() throws ParkingNotFoundException {
        Mockito.when(parkingPlaceManagementService.addParkingPlace(parkingPlace)).thenReturn(parkingPlace);
        assertEquals(parkingPlaceManagementController.addParkingPlace(parkingPlace), parkingPlace);
        Mockito.verify(parkingPlaceManagementService, Mockito.times(1)).addParkingPlace(parkingPlace);
    }
}
