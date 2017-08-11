package com.mobica.rnd.parking.parkingbe.repository;

import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingPlaceRepository extends MongoRepository<ParkingPlace, String> {
}
