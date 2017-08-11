package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.exception.ParkingNotFoundException;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import com.mobica.rnd.parking.parkingbe.repository.ParkingPlaceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ParkingPlaceManagementServiceTest {
    @InjectMocks
    private ParkingPlaceManagementService parkingPlaceManagementService;

    @Mock
    private ParkingPlace parkingPlace;

    @Mock
    private ParkingPlaceValidationService parkingPlaceValidationService;

    @Mock
    private ParkingPlaceRepository parkingPlaceRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addParkingPlaceTest() throws ParkingNotFoundException {
        Mockito.when(parkingPlaceRepository.insert(parkingPlace)).thenReturn(parkingPlace);
        Mockito.doNothing().when(parkingPlaceValidationService).validateParkingPlace(parkingPlace);
        assertEquals(parkingPlace, parkingPlaceManagementService.addParkingPlace(parkingPlace));
        Mockito.verify(parkingPlaceRepository, Mockito.times(1)).insert(parkingPlace);
        Mockito.verify(parkingPlaceValidationService, Mockito.times(1)).validateParkingPlace(parkingPlace);
    }

    @Test(expected = ParkingNotFoundException.class)
    public void parkingNotFoundTest() throws ParkingNotFoundException {
        Mockito.doThrow(ParkingNotFoundException.class).when(parkingPlaceValidationService).validateParkingPlace(parkingPlace);
        parkingPlaceManagementService.addParkingPlace(parkingPlace);
    }
}
