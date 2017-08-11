package com.mobica.rnd.parking.parkingbe.repository;


import com.mobica.rnd.parking.parkingbe.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
}
