package com.mobica.rnd.parking.parkingbe.repository;

import com.mobica.rnd.parking.parkingbe.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {

  @Query("{'owner.userId' : ?0}")
  List<Car> findByUserId(String userId);
}
