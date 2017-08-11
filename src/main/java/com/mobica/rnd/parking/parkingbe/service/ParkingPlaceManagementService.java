package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.exception.ParkingNotFoundException;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import com.mobica.rnd.parking.parkingbe.repository.ParkingPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingPlaceManagementService {
    private ParkingPlaceRepository parkingPlaceRepository;
    private ParkingPlaceValidationService parkingPlaceValidationService;

    @Autowired
    public ParkingPlaceManagementService(ParkingPlaceRepository parkingPlaceRepository, ParkingPlaceValidationService parkingPlaceValidationService) {
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.parkingPlaceValidationService = parkingPlaceValidationService;
    }

    public ParkingPlace addParkingPlace(ParkingPlace parkingPlace) throws ParkingNotFoundException {
        parkingPlaceValidationService.validateParkingPlace(parkingPlace);
        return parkingPlaceRepository.insert(parkingPlace);
    }
}
