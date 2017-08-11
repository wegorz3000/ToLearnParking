package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.exception.CarException;
import com.mobica.rnd.parking.parkingbe.model.Car;
import com.mobica.rnd.parking.parkingbe.model.CarExceptionCode;
import com.mobica.rnd.parking.parkingbe.repository.CarRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
@NoArgsConstructor
public class CarsManagementService {
    private CarRepository carRepository;

    @Autowired
    public CarsManagementService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car addCar(Car c) {
        if (c.getActiveState() == null) {
            c.setActiveState(true);
        }
        return carRepository.insert(c);
    }

    public Car updateActiveState(String id, Car c) throws CarException {
        if (c.getActiveState() != null) {
            Car car = findCarById(id);
            car.setActiveState(c.getActiveState());
            return carRepository.save(car);
        } else {
            throw new CarException(CarExceptionCode.NULL_ACTIVE_STATE);
        }
    }

    Car findCarById(String id) throws CarException {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            return car.get();
        } else throw new CarException(CarExceptionCode.CAR_NOT_FOUND);
    }
    public List<Car> findByUser(String userId) {
        return carRepository.findByUserId(userId);
    }
}
