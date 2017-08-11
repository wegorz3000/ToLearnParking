package com.mobica.rnd.parking.parkingbe.controller;

import com.mobica.rnd.parking.parkingbe.exception.ParkingNotFoundException;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import com.mobica.rnd.parking.parkingbe.service.ParkingPlaceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/parking/place/")
public class ParkingPlaceManagementController {
    private ParkingPlaceManagementService parkingPlaceManagementService;

    @Autowired
    public ParkingPlaceManagementController(ParkingPlaceManagementService parkingPlaceManagementService) {
        this.parkingPlaceManagementService = parkingPlaceManagementService;
    }

    @PostMapping
    public ParkingPlace addParkingPlace(@RequestBody @Valid ParkingPlace parkingPlace) throws ParkingNotFoundException {
        return parkingPlaceManagementService.addParkingPlace(parkingPlace);
    }
}
