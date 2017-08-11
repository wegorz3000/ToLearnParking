package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.model.Car;
import com.mobica.rnd.parking.parkingbe.model.EmptyReservation;
import com.mobica.rnd.parking.parkingbe.model.Reservation;
import com.mobica.rnd.parking.parkingbe.repository.EmptyReservationRepository;
import com.mobica.rnd.parking.parkingbe.repository.ReservationRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@NoArgsConstructor
@PropertySource("classpath:ValidationMessages.properties")
public class ReservationServiceImpl {
    private ReservationRepository reservationRepository;
     private MongoTemplate mongoTemplate;
    private EmptyReservationRepository emptyReservationRepository;
    private Car car;

    @Value("${msg.validation.reservation.nonactivecar}")
    private String nonActiveCarError;
    @Value("${msg.validation.date.wrongdate}")
    private String wrongDateError;
    @Value("${msg.validation.car.cardoesntexist}")
    private String carDoesntExistError;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, MongoTemplate mongoTemplate, EmptyReservationRepository emptyReservationRepository) {
        this.reservationRepository = reservationRepository;
        this.mongoTemplate = mongoTemplate;
        this.emptyReservationRepository = emptyReservationRepository;
    }

    public ResponseEntity<?> reserveParkingPlace(Reservation reservation) {
        Map<String, String> response = new HashMap<>();

        if (checkIsCarExist(reservation)) {
            response.put("error", carDoesntExistError);
            return ResponseEntity.badRequest().body(response);
        } else
            car = getCarFromDataBase(reservation);

        if (!car.getActiveState()) {
            response.put("error", nonActiveCarError);
            return ResponseEntity.badRequest().body(response);
        }
        if (reservationDateIsAvailable(reservation) || reservation.getStart().isAfter(reservation.getEnd())) {
            response.put("error", wrongDateError);
            return ResponseEntity.badRequest().body(response);
        }
        addReservationToRepository(reservation);
        deleteEmptyReservations(reservation);
        return ResponseEntity.ok(reservation);
    }


    public void addReservationToRepository(Reservation reservation) {
        reservationRepository.insert(reservation);
    }

    public Car getCarFromDataBase(Reservation reservation) {
        return mongoTemplate.findOne(new Query()
                        .addCriteria(Criteria.where("id").is(reservation.getCar().getId())),
                Car.class);
    }

    public void deleteEmptyReservations(Reservation reservation) {
        int counter = 0;
        while (reservation.getStart().plusDays(counter).isBefore(reservation.getEnd().plusDays(1))) {
            EmptyReservation emptyReservation = mongoTemplate.findOne(new Query()
                            .addCriteria(Criteria.where("date").is(reservation.getStart().plusDays(counter)))
                            .addCriteria(Criteria.where("parkingPlace").is(reservation.getParkingPlace().getParkingPlaceId())),
                    EmptyReservation.class);
            emptyReservationRepository.delete(emptyReservation);
            counter++;
        }
    }

    public boolean checkIsCarExist(Reservation reservation) {
        if (!mongoTemplate.exists(new Query().addCriteria(Criteria.where("id").is(reservation.getCar().getId())), Car.class))
            return true;
        return false;
    }

    public boolean reservationDateIsAvailable(Reservation reservation) {
        int counter = 0;
        while (reservation.getStart().plusDays(counter).isBefore(reservation.getEnd().plusDays(1))) {
            if (!mongoTemplate.exists(new Query()
                            .addCriteria(Criteria.where("date").is(reservation.getStart().plusDays(counter)))
                            .addCriteria(Criteria.where("parkingPlace").is(reservation.getParkingPlace().getParkingPlaceId())),
                    EmptyReservation.class))
                return true;
            counter++;
        }
        return false;
    }
}
