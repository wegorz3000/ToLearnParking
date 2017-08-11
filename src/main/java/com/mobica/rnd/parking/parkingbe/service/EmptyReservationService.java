package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.exception.MarkAvailableSlotsException;
import com.mobica.rnd.parking.parkingbe.model.EmptyReservation;
import com.mobica.rnd.parking.parkingbe.model.MarkAvailableSlotsExceptionCode;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import com.mobica.rnd.parking.parkingbe.model.Reservation;
import com.mobica.rnd.parking.parkingbe.repository.EmptyReservationRepository;
import com.mobica.rnd.parking.parkingbe.repository.ParkingPlaceRepository;
import com.mobica.rnd.parking.parkingbe.repository.ReservationRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@NoArgsConstructor
@PropertySource("classpath:ValidationMessages.properties")
public class EmptyReservationService {
    private EmptyReservationRepository emptyReservationRepository;
    private ParkingPlaceRepository parkingPlaceRepository;
    private ReservationRepository reservationRepository;
    private MongoTemplate mongoTemplate;

    @Value("${msg.result.emptyreservation.add}")
    private String addMessage;

    @Autowired
    public EmptyReservationService(EmptyReservationRepository emptyReservationRepository,
                                   ParkingPlaceRepository parkingPlaceRepository,
                                   ReservationRepository reservationRepository,
                                   MongoTemplate mongoTemplate) {
        this.emptyReservationRepository = emptyReservationRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.reservationRepository = reservationRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Map<String, String> markAvailableParkingPlaces(List<ParkingPlace> parkingPlaces, LocalDate start, LocalDate end) throws MarkAvailableSlotsException {
        Map<String, String> result = new HashMap<>();
        checkIfEndDateNotBeforeStart(start, end);
        verifyParkingPlacesExist(parkingPlaces);
        verifyParkingPlacesAlreadyBooked(parkingPlaces, start, end);
        verifyParkingPlacesAlreadyAvailable(parkingPlaces, start, end);
        addEmptyReservations(parkingPlaces, start, end);
        result.put("success", addMessage);
        return result;
    }

    void verifyParkingPlacesAlreadyAvailable(List<ParkingPlace> parkingPlaces, LocalDate start, LocalDate end) throws MarkAvailableSlotsException {
        for (ParkingPlace parkingPlace : parkingPlaces) {
            if (mongoTemplate.exists(new Query()
                            .addCriteria(Criteria.where("parkingPlace").is(parkingPlace))
                            .addCriteria(Criteria.where("date").gte(start).lte(end)),
                    EmptyReservation.class)) {
                throw new MarkAvailableSlotsException(MarkAvailableSlotsExceptionCode.ALREADY_AVAILABLE);
            }
        }
    }

    void verifyParkingPlacesAlreadyBooked(List<ParkingPlace> parkingPlaces, LocalDate start, LocalDate end) throws MarkAvailableSlotsException {
        for (ParkingPlace parkingPlace : parkingPlaces) {
            if (mongoTemplate.exists(new Query()
                            .addCriteria(Criteria.where("parkingPlace").is(parkingPlace))
                            .addCriteria(Criteria.where("start").gte(start).lte(end).orOperator(Criteria.where("end").gte(start).lte(end))),
                    Reservation.class)) {
                throw new MarkAvailableSlotsException(MarkAvailableSlotsExceptionCode.PARKING_PLACE_BOOKED);
            }
        }
    }

    void verifyParkingPlacesExist(List<ParkingPlace> parkingPlaces) throws MarkAvailableSlotsException {
        for (ParkingPlace parkingPlace : parkingPlaces) {
            if (!ifParkingPlaceExists(parkingPlace)) {
                throw new MarkAvailableSlotsException(MarkAvailableSlotsExceptionCode.PARKING_PLACE_NOT_FOUND);
            }
        }
    }

    void addEmptyReservations(List<ParkingPlace> parkingPlaces, LocalDate start, LocalDate end) {
        for (ParkingPlace parkingPlace : parkingPlaces) {
            addEmptyReservationsForParkingPlace(parkingPlace, start, end);
        }
    }

    boolean ifParkingPlaceExists(ParkingPlace parkingPlace) {
        return parkingPlaceRepository.existsById(parkingPlace.getParkingPlaceId());
    }

    void addEmptyReservationsForParkingPlace(ParkingPlace parkingPlace, LocalDate start, LocalDate end) {
        for (int i = 0; i <= ChronoUnit.DAYS.between(start, end); i++) {
            emptyReservationRepository.insert(new EmptyReservation(parkingPlace, start.plusDays(i)));
        }
    }

    void checkIfEndDateNotBeforeStart(LocalDate start, LocalDate end) throws MarkAvailableSlotsException {
        if (end.isBefore(start)) {
            throw new MarkAvailableSlotsException(MarkAvailableSlotsExceptionCode.END_DATE_BEFORE_START);
        }
    }
}
