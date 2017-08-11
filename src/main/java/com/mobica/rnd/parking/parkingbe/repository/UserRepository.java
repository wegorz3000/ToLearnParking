package com.mobica.rnd.parking.parkingbe.repository;

import com.mobica.rnd.parking.parkingbe.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}

