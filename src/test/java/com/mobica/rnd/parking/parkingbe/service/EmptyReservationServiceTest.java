package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.ParkingBeApplication;
import com.mobica.rnd.parking.parkingbe.exception.MarkAvailableSlotsException;
import com.mobica.rnd.parking.parkingbe.model.EmptyReservation;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import com.mobica.rnd.parking.parkingbe.repository.EmptyReservationRepository;
import com.mobica.rnd.parking.parkingbe.repository.ParkingPlaceRepository;
import com.mobica.rnd.parking.parkingbe.repository.ReservationRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {ParkingBeApplication.class})
@TestPropertySource("classpath:ValidationMessages.properties")
public class EmptyReservationServiceTest {
    @Value("${msg.result.emptyreservation.add}")
    private String addMessage;

    @Spy
    private EmptyReservationService emptyReservationService;

    @Mock
    private List<ParkingPlace> parkingPlaces;

    @Mock
    private EmptyReservationRepository emptyReservationRepository;

    @Mock
    private ParkingPlaceRepository parkingPlaceRepository;

    @Mock
    private EmptyReservation emptyReservation;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private ParkingPlace parkingPlace;

    private LocalDate now;
    private LocalDate beforeNow;
    private LocalDate twoDaysAfterNow;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(emptyReservationService, "addMessage", addMessage);
        ReflectionTestUtils.setField(emptyReservationService, "parkingPlaceRepository", parkingPlaceRepository);
        ReflectionTestUtils.setField(emptyReservationService, "emptyReservationRepository", emptyReservationRepository);
        ReflectionTestUtils.setField(emptyReservationService, "mongoTemplate", mongoTemplate);
        now = LocalDate.now();
        beforeNow = LocalDate.now().minusDays(1);
        twoDaysAfterNow = LocalDate.now().plusDays(2);
    }

    @Test
    public void markAvailableParkingPlacesPositiveTest() throws MarkAvailableSlotsException {
        Mockito.doNothing().when(emptyReservationService).addEmptyReservations(parkingPlaces, now, now);
        Mockito.doNothing().when(emptyReservationService).verifyParkingPlacesAlreadyAvailable(parkingPlaces, now, now);
        Mockito.doNothing().when(emptyReservationService).verifyParkingPlacesAlreadyBooked(parkingPlaces, now, now);
        Mockito.doNothing().when(emptyReservationService).verifyParkingPlacesExist(parkingPlaces);
        Map<String, String> result = new HashMap<>();
        result.put("success", addMessage);
        assertEquals(result, emptyReservationService.markAvailableParkingPlaces(parkingPlaces, now, now));
        Mockito.verify(emptyReservationService, Mockito.times(1)).addEmptyReservations(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).checkIfEndDateNotBeforeStart(now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).verifyParkingPlacesAlreadyAvailable(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).verifyParkingPlacesAlreadyBooked(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).verifyParkingPlacesExist(parkingPlaces);
    }

    @Test(expected = MarkAvailableSlotsException.class)
    public void markAvailableParkingPlacesEndBeforeStartTest() throws MarkAvailableSlotsException {
        emptyReservationService.markAvailableParkingPlaces(parkingPlaces, twoDaysAfterNow, beforeNow);
        Mockito.verify(emptyReservationService, Mockito.times(1)).checkIfEndDateNotBeforeStart(twoDaysAfterNow, beforeNow);
        Mockito.verify(emptyReservationService, Mockito.times(0)).verifyParkingPlacesAlreadyAvailable(parkingPlaces, twoDaysAfterNow, beforeNow);
        Mockito.verify(emptyReservationService, Mockito.times(0)).verifyParkingPlacesAlreadyBooked(parkingPlaces, twoDaysAfterNow, beforeNow);
        Mockito.verify(emptyReservationService, Mockito.times(0)).verifyParkingPlacesExist(parkingPlaces);
        Mockito.verify(emptyReservationService, Mockito.times(0)).addEmptyReservations(parkingPlaces, twoDaysAfterNow, beforeNow);
    }

    @Test(expected = MarkAvailableSlotsException.class)
    public void markAvailableParkingPlacesNegativeVerifyParkingPlacesExistTest() throws MarkAvailableSlotsException {
        Mockito.doThrow(MarkAvailableSlotsException.class).when(emptyReservationService).verifyParkingPlacesExist(parkingPlaces);
        emptyReservationService.markAvailableParkingPlaces(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(0)).addEmptyReservations(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).checkIfEndDateNotBeforeStart(now, now);
        Mockito.verify(emptyReservationService, Mockito.times(0)).verifyParkingPlacesAlreadyAvailable(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(0)).verifyParkingPlacesAlreadyBooked(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).verifyParkingPlacesExist(parkingPlaces);
    }

    @Test(expected = MarkAvailableSlotsException.class)
    public void markAvailableParkingPlacesNegativeVerifyParkingPlacesAlreadyAvailableTest() throws MarkAvailableSlotsException {
        Mockito.doThrow(MarkAvailableSlotsException.class).when(emptyReservationService).verifyParkingPlacesAlreadyAvailable(parkingPlaces, now, now);
        Mockito.doNothing().when(emptyReservationService).verifyParkingPlacesExist(parkingPlaces);
        Mockito.doNothing().when(emptyReservationService).verifyParkingPlacesAlreadyBooked(parkingPlaces, now, now);
        emptyReservationService.markAvailableParkingPlaces(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(0)).addEmptyReservations(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).checkIfEndDateNotBeforeStart(now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).verifyParkingPlacesAlreadyAvailable(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).verifyParkingPlacesAlreadyBooked(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).verifyParkingPlacesExist(parkingPlaces);
    }

    @Test(expected = MarkAvailableSlotsException.class)
    public void markAvailableParkingPlacesNegativeVerifyParkingPlacesAlreadyBookedTest() throws MarkAvailableSlotsException {
        Mockito.doThrow(MarkAvailableSlotsException.class).when(emptyReservationService).verifyParkingPlacesAlreadyBooked(parkingPlaces, now, now);
        Mockito.doNothing().when(emptyReservationService).verifyParkingPlacesExist(parkingPlaces);
        emptyReservationService.markAvailableParkingPlaces(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(0)).addEmptyReservations(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).checkIfEndDateNotBeforeStart(now, now);
        Mockito.verify(emptyReservationService, Mockito.times(0)).verifyParkingPlacesAlreadyAvailable(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).verifyParkingPlacesAlreadyBooked(parkingPlaces, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).verifyParkingPlacesExist(parkingPlaces);
    }

    @Test(expected = MarkAvailableSlotsException.class)
    public void checkIfEndDateNotBeforeStartNegativeTest() throws MarkAvailableSlotsException {
        emptyReservationService.checkIfEndDateNotBeforeStart(now, beforeNow);
        Mockito.verify(emptyReservationService, Mockito.times(1)).checkIfEndDateNotBeforeStart(now, beforeNow);
    }

    @Test
    public void checkIfEndDateNotBeforeStartSameDayTest() throws MarkAvailableSlotsException {
        emptyReservationService.checkIfEndDateNotBeforeStart(now, now);
        Mockito.verify(emptyReservationService, Mockito.times(1)).checkIfEndDateNotBeforeStart(now, now);
    }

    @Test
    public void checkIfEndDateNotBeforeStartEndAfterStartTest() throws MarkAvailableSlotsException {
        emptyReservationService.checkIfEndDateNotBeforeStart(now, twoDaysAfterNow);
        Mockito.verify(emptyReservationService, Mockito.times(1)).checkIfEndDateNotBeforeStart(now, twoDaysAfterNow);
    }

    @Test(expected = MarkAvailableSlotsException.class)
    public void verifyParkingPlacesExistNegativeTest() throws MarkAvailableSlotsException {
        List<ParkingPlace> parkingPlaceList = Arrays.asList(parkingPlace, parkingPlace, parkingPlace);
        Mockito.doReturn(false).when(emptyReservationService).ifParkingPlaceExists(parkingPlace);
        emptyReservationService.verifyParkingPlacesExist(parkingPlaceList);
        Mockito.verify(emptyReservationService, Mockito.times(1)).ifParkingPlaceExists(any(ParkingPlace.class));
    }

    @Test
    public void verifyParkingPlacesExistPositiveTest() throws MarkAvailableSlotsException {
        List<ParkingPlace> parkingPlaceList = Arrays.asList(parkingPlace, parkingPlace, parkingPlace);
        Mockito.doReturn(true).when(emptyReservationService).ifParkingPlaceExists(parkingPlace);
        emptyReservationService.verifyParkingPlacesExist(parkingPlaceList);
        Mockito.verify(emptyReservationService, Mockito.times(3)).ifParkingPlaceExists(any(ParkingPlace.class));
    }

    @Test(expected = MarkAvailableSlotsException.class)
    public void verifyParkingPlacesExistMixedTest() throws MarkAvailableSlotsException {
        ParkingPlace notExistingParkingPlace = new ParkingPlace();
        List<ParkingPlace> parkingPlaceList = Arrays.asList(parkingPlace, notExistingParkingPlace, parkingPlace);
        Mockito.doReturn(true).when(emptyReservationService).ifParkingPlaceExists(parkingPlace);
        Mockito.doReturn(false).when(emptyReservationService).ifParkingPlaceExists(notExistingParkingPlace);
        emptyReservationService.verifyParkingPlacesExist(parkingPlaceList);
        Mockito.verify(emptyReservationService, Mockito.times(2)).ifParkingPlaceExists(any(ParkingPlace.class));
    }

    @Test
    public void addEmptyReservationsForParkingPlaceOneDayTest() {
        Mockito.when(emptyReservationRepository.insert(any(EmptyReservation.class))).thenReturn(emptyReservation);
        emptyReservationService.addEmptyReservationsForParkingPlace(parkingPlace, now, now);
        Mockito.verify(emptyReservationRepository, Mockito.times(1)).insert(any(EmptyReservation.class));
    }

    @Test
    public void addEmptyReservationsForParkingPlaceThreeDaysTest() {
        Mockito.when(emptyReservationRepository.insert(any(EmptyReservation.class))).thenReturn(emptyReservation);
        emptyReservationService.addEmptyReservationsForParkingPlace(parkingPlace, now, twoDaysAfterNow);
        Mockito.verify(emptyReservationRepository, Mockito.times(3)).insert(any(EmptyReservation.class));
    }

    @Test
    public void addEmptyReservationsOneDayTest() {
        Mockito.when(emptyReservationRepository.insert(any(EmptyReservation.class))).thenReturn(emptyReservation);
        List<ParkingPlace> parkingPlaceList = Arrays.asList(parkingPlace, parkingPlace, parkingPlace);
        emptyReservationService.addEmptyReservations(parkingPlaceList, now, now);
        Mockito.verify(emptyReservationService, Mockito.times(3)).addEmptyReservationsForParkingPlace(parkingPlace, now, now);
        Mockito.verify(emptyReservationRepository, Mockito.times(3)).insert(any(EmptyReservation.class));
    }

    @Test
    public void addEmptyReservationsThreeDayTest() {
        Mockito.when(emptyReservationRepository.insert(any(EmptyReservation.class))).thenReturn(emptyReservation);
        List<ParkingPlace> parkingPlaceList = Arrays.asList(parkingPlace, parkingPlace, parkingPlace);
        emptyReservationService.addEmptyReservations(parkingPlaceList, now, twoDaysAfterNow);
        Mockito.verify(emptyReservationService, Mockito.times(3)).addEmptyReservationsForParkingPlace(parkingPlace, now, twoDaysAfterNow);
        Mockito.verify(emptyReservationRepository, Mockito.times(9)).insert(any(EmptyReservation.class));
    }

    @Test
    public void ifParkingExistsPositiveTest() {
        ParkingPlace place = new ParkingPlace();
        place.setParkingPlaceId(RandomStringUtils.randomAlphanumeric(1));
        Mockito.when(parkingPlaceRepository.existsById(any(String.class))).thenReturn(true);
        assertTrue(emptyReservationService.ifParkingPlaceExists(place));
        Mockito.verify(parkingPlaceRepository, Mockito.times(1)).existsById(any(String.class));
    }

    @Test
    public void ifParkingExistsNegativeTest() {
        ParkingPlace place = new ParkingPlace();
        place.setParkingPlaceId(RandomStringUtils.randomAlphanumeric(1));
        Mockito.when(parkingPlaceRepository.existsById(any(String.class))).thenReturn(false);
        assertFalse(emptyReservationService.ifParkingPlaceExists(place));
        Mockito.verify(parkingPlaceRepository, Mockito.times(1)).existsById(any(String.class));
    }

    @Test
    public void verifyParkingPlacesAlreadyAvailablePositiveTest() throws MarkAvailableSlotsException {
        Mockito.when(mongoTemplate.exists(any(Query.class),any(Class.class))).thenReturn(false);
        List<ParkingPlace> parkingPlaceList = Arrays.asList(parkingPlace, parkingPlace, parkingPlace);
        emptyReservationService.verifyParkingPlacesAlreadyAvailable(parkingPlaceList, now, now);
        Mockito.verify(mongoTemplate, Mockito.times(3)).exists(any(Query.class),any(Class.class));
    }

    @Test(expected = MarkAvailableSlotsException.class)
    public void verifyParkingPlacesAlreadyAvailableNegativeTest() throws MarkAvailableSlotsException {
        Mockito.when(mongoTemplate.exists(any(Query.class),any(Class.class))).thenReturn(true);
        List<ParkingPlace> parkingPlaceList = Arrays.asList(parkingPlace, parkingPlace, parkingPlace);
        emptyReservationService.verifyParkingPlacesAlreadyAvailable(parkingPlaceList, now, now);
        Mockito.verify(mongoTemplate, Mockito.times(1)).exists(any(Query.class),any(Class.class));
    }

    @Test
    public void verifyParkingPlacesAlreadyBookedPositiveTest() throws MarkAvailableSlotsException {
        Mockito.when(mongoTemplate.exists(any(Query.class),any(Class.class))).thenReturn(false);
        List<ParkingPlace> parkingPlaceList = Arrays.asList(parkingPlace, parkingPlace, parkingPlace);
        emptyReservationService.verifyParkingPlacesAlreadyBooked(parkingPlaceList, now, now);
        Mockito.verify(mongoTemplate, Mockito.times(3)).exists(any(Query.class),any(Class.class));
    }

    @Test(expected = MarkAvailableSlotsException.class)
    public void verifyParkingPlacesAlreadyBookedNegativeTest() throws MarkAvailableSlotsException {
        Mockito.when(mongoTemplate.exists(any(Query.class),any(Class.class))).thenReturn(true);
        List<ParkingPlace> parkingPlaceList = Arrays.asList(parkingPlace, parkingPlace, parkingPlace);
        emptyReservationService.verifyParkingPlacesAlreadyBooked(parkingPlaceList, now, now);
        Mockito.verify(mongoTemplate, Mockito.times(1)).exists(any(Query.class),any(Class.class));
    }
}
