package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.exception.ParkingNotFoundException;
import com.mobica.rnd.parking.parkingbe.model.Parking;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import com.mobica.rnd.parking.parkingbe.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingPlaceValidationService {
    private ParkingRepository parkingRepository;

    @Autowired
    public ParkingPlaceValidationService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public void validateParkingPlace(ParkingPlace parkingPlace) throws ParkingNotFoundException {
        if (!validateParking(parkingPlace)) throw new ParkingNotFoundException();
    }

    private boolean validateParking(ParkingPlace parkingPlace) {
        Optional<Parking> parking = parkingRepository.findById(parkingPlace.getParking().getId());
        if (parking.isPresent()) {
            parkingPlace.setParking(parking.get());
            return true;
        } else return false;
    }

}
