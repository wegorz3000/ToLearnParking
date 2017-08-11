package com.mobica.rnd.parking.parkingbe.service;


import com.mobica.rnd.parking.parkingbe.model.Parking;
import com.mobica.rnd.parking.parkingbe.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingServiceImpl implements ParkingService {

    private ParkingRepository parkingRepository;

    ParkingServiceImpl(@Autowired ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public Parking defineNewParking(Parking parking) {
        return parkingRepository.insert(parking);
    }
}
