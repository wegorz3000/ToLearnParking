package com.mobica.rnd.parking.parkingbe.repository;


import com.mobica.rnd.parking.parkingbe.model.Parking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingRepository extends MongoRepository<Parking, String> {
}
