package com.mobica.rnd.parking.parkingbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.mobica.rnd.parking.parkingbe.repository")
public class ParkingBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingBeApplication.class, args);

    }
}
