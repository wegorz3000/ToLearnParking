package com.mobica.rnd.parking.parkingbe.controller;

import com.mobica.rnd.parking.parkingbe.model.Parking;
import com.mobica.rnd.parking.parkingbe.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/parking")
public class ParkingController {
    private ParkingService parkingService;

    ParkingController(@Autowired ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping
    public Parking defineNewParking(@RequestBody @Valid Parking parking) {
        return parkingService.defineNewParking(parking);
    }

}
