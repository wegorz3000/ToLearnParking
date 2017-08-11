package com.mobica.rnd.parking.parkingbe.repository;

import com.mobica.rnd.parking.parkingbe.model.EmptyReservation;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmptyReservationRepository extends MongoRepository<EmptyReservation, String> {
}
