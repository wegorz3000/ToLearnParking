package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.ParkingBeApplication;
import com.mobica.rnd.parking.parkingbe.model.Car;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import com.mobica.rnd.parking.parkingbe.model.Reservation;
import com.mobica.rnd.parking.parkingbe.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ParkingBeApplication.class})
@TestPropertySource("classpath:ValidationMessages.properties")
public class ReservationServiceTest {

    @Value("${msg.validation.reservation.nonactivecar}")
    private String nonActiveCarError;
    @Value("${msg.validation.date.wrongdate}")
    private String wrongDateError;
    @Value("${msg.validation.car.cardoesntexist}")
    private String carDoesntExistError;

    @Mock
    private Reservation reservationInstance;
    @Mock
    private Car car;
    @Mock
    private ParkingPlace parkingPlace;
    @Spy
    private ReservationServiceImpl reservationService;

    LocalDate startDate;
    LocalDate endDate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(reservationService, "nonActiveCarError", nonActiveCarError);
        ReflectionTestUtils.setField(reservationService, "wrongDateError", wrongDateError);
        ReflectionTestUtils.setField(reservationService, "carDoesntExistError", carDoesntExistError);
        given(car.getActiveState()).willReturn(true);
        startDate = LocalDate.now();
        endDate = LocalDate.now().plusDays(2);

    }


    @Test
    public void reserveParkingPlace_CorrectData_Test() {
        Reservation reservation = Reservation.builder()
                .id(RandomStringUtils.randomAlphanumeric(10))
                .car(car)
                .parkingPlace(parkingPlace)
                .start(startDate)
                .end(endDate)
                .build();

        doReturn(false).when(reservationService).reservationDateIsAvailable(reservation);
        doReturn(false).when(reservationService).checkIsCarExist(reservation);
        Mockito.doNothing().when(reservationService).addReservationToRepository(reservation);
        Mockito.doNothing().when(reservationService).deleteEmptyReservations(any(Reservation.class));
        doReturn(car).when(reservationService).getCarFromDataBase(reservation);
        Assert.assertEquals(HttpStatus.OK, reservationService.reserveParkingPlace(reservation).getStatusCode());
    }

    @Test
    public void reserveParkingPlace_StartDateAfterEndDate_Test() {
        HashMap<String, String> expectedResponse = new HashMap<String, String>();
        expectedResponse.put("error", wrongDateError);

        Reservation reservation = Reservation.builder()
                .id(RandomStringUtils.randomAlphanumeric(10))
                .car(car)
                .parkingPlace(parkingPlace)
                .start(endDate)
                .end(startDate)
                .build();

        doReturn(false).when(reservationService).reservationDateIsAvailable(reservation);
        doReturn(false).when(reservationService).checkIsCarExist(reservation);
        doReturn(car).when(reservationService).getCarFromDataBase(reservation);
        Mockito.doNothing().when(reservationService).addReservationToRepository(reservation);
        Mockito.doNothing().when(reservationService).deleteEmptyReservations(any(Reservation.class));
        Assert.assertEquals(expectedResponse, reservationService.reserveParkingPlace(reservation).getBody());
    }

    @Test
    public void reserveParkingPlace_DateIsNotAvailable_Test() {
        HashMap<String, String> expectedResponse = new HashMap<String, String>();
        expectedResponse.put("error", wrongDateError);

        Reservation reservation = Reservation.builder()
                .id(RandomStringUtils.randomAlphanumeric(10))
                .car(car)
                .parkingPlace(parkingPlace)
                .start(startDate)
                .end(endDate)
                .build();

        doReturn(true).when(reservationService).reservationDateIsAvailable(reservation);
        doReturn(false).when(reservationService).checkIsCarExist(reservation);
        Mockito.doNothing().when(reservationService).addReservationToRepository(reservation);
       doReturn(car).when(reservationService).getCarFromDataBase(reservation);
        Mockito.doNothing().when(reservationService).deleteEmptyReservations(any(Reservation.class));
        Assert.assertEquals(expectedResponse, reservationService.reserveParkingPlace(reservation).getBody());
    }

    @Test
    public void reserveParkingPlace_InactiveCarError_Test() {
        HashMap<String, String> expectedResponse = new HashMap<String, String>();
        expectedResponse.put("error", nonActiveCarError);

        Car carWithInactiveState = Car.builder()
                .id(RandomStringUtils.randomAlphanumeric(5))
                .owner(new User())
                .brand(RandomStringUtils.randomAlphanumeric(5))
                .model(RandomStringUtils.randomAlphanumeric(5))
                .color(RandomStringUtils.randomAlphanumeric(5))
                .plateNumber(RandomStringUtils.randomAlphanumeric(5))
                .activeState(false)
                .build();
        Reservation reservation = Reservation.builder()
                .id(RandomStringUtils.randomAlphanumeric(10))
                .car(carWithInactiveState)
                .parkingPlace(parkingPlace)
                .start(LocalDate.now())
                .end(LocalDate.now().plusDays(2))
                .build();
        doReturn(false).when(reservationService).reservationDateIsAvailable(reservation);
        doReturn(false).when(reservationService).checkIsCarExist(reservation);
        Mockito.doNothing().when(reservationService).addReservationToRepository(reservation);
        doReturn(carWithInactiveState).when(reservationService).getCarFromDataBase(reservation);
        Mockito.doNothing().when(reservationService).deleteEmptyReservations(any(Reservation.class));
        Assert.assertEquals(expectedResponse, reservationService.reserveParkingPlace(reservation).getBody());
    }

    @Test
    public void reserveParkingPlace_CarDoesntExistError_Test() {
        HashMap<String, String> expectedResponse = new HashMap<String, String>();
        expectedResponse.put("error", carDoesntExistError);

        Reservation reservation = Reservation.builder()
                .id(RandomStringUtils.randomAlphanumeric(10))
                .car(car)
                .parkingPlace(parkingPlace)
                .start(LocalDate.now())
                .end(LocalDate.now().plusDays(2))
                .build();
        doReturn(false).when(reservationService).reservationDateIsAvailable(reservation);
        doReturn(true).when(reservationService).checkIsCarExist(reservation);
        Mockito.doNothing().when(reservationService).addReservationToRepository(reservation);
        Mockito.doNothing().when(reservationService).deleteEmptyReservations(any(Reservation.class));
        Assert.assertEquals(expectedResponse, reservationService.reserveParkingPlace(reservation).getBody());
    }

    @Test
    public void addReservationToRepositoryMethodTest() {
        Mockito.doNothing().when(reservationService).addReservationToRepository(any(Reservation.class));
        reservationService.addReservationToRepository(reservationInstance);
        Mockito.verify(reservationService, Mockito.times(1)).addReservationToRepository(reservationInstance);
    }

    @Test
    public void deleteEmptyReservationsMethodTest() {
        Mockito.doNothing().when(reservationService).deleteEmptyReservations(any(Reservation.class));
        reservationService.deleteEmptyReservations(reservationInstance);
        Mockito.verify(reservationService, Mockito.times(1)).deleteEmptyReservations(reservationInstance);
    }

    @Test
    public void checkIsCarExistMethodTest() {
        doReturn(true).when(reservationService).checkIsCarExist(reservationInstance);
        Assert.assertEquals(true, reservationService.checkIsCarExist(reservationInstance));
    }

    @Test
    public void reservationDateIsAvailableMethodTest() {
        doReturn(true).when(reservationService).reservationDateIsAvailable(reservationInstance);
        Assert.assertEquals(true, reservationService.reservationDateIsAvailable(reservationInstance));
    }
}
