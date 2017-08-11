package com.mobica.rnd.parking.parkingbe.controller;

import com.mobica.rnd.parking.parkingbe.exception.MarkAvailableSlotsException;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import com.mobica.rnd.parking.parkingbe.model.Reservation;
import com.mobica.rnd.parking.parkingbe.service.EmptyReservationService;
import com.mobica.rnd.parking.parkingbe.service.ReservationServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReservationControllerTest {
    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private EmptyReservationService emptyReservationService;
    @Mock
    private ReservationServiceImpl reservationService;

    @Mock
    private List<ParkingPlace> parkingPlaces;

    @Mock
    private Map<String, String> map;

    @Test
    public void markAvailableParkingPlacesTest() throws MarkAvailableSlotsException {
        Optional<LocalDate> optional = Optional.of(LocalDate.now());
        Mockito.when(emptyReservationService.markAvailableParkingPlaces(parkingPlaces, optional.get(), optional.get())).thenReturn(map);
        assertEquals(map,reservationController.markAvailableParkingPlaces(parkingPlaces, optional, optional));
    }

    @Test
    public void reserveMethodIsWorkingTest() {
        Reservation reservation = new Reservation();
        reservationController.reserve(reservation);
        Mockito.verify(reservationService, Mockito.times(1)).reserveParkingPlace(reservation);
    }

    @Test
    public void reserveNullDataTest() {
        Reservation reservation = new Reservation();
        Assert.assertEquals(reservationController.reserve(reservation) , null);

    }

}
