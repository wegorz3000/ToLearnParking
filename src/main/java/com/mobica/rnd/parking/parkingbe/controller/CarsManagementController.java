package com.mobica.rnd.parking.parkingbe.controller;

import com.mobica.rnd.parking.parkingbe.exception.CarException;
import com.mobica.rnd.parking.parkingbe.model.Car;
import com.mobica.rnd.parking.parkingbe.service.CarsManagementService;
import com.mobica.rnd.parking.parkingbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/car/")
public class CarsManagementController {
    private CarsManagementService carsManagementService;
    private UserService userService;

    @Autowired
    public CarsManagementController(CarsManagementService carsManagementService,UserService userService) {
        this.carsManagementService = carsManagementService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public Car addCar(@RequestBody @Valid Car car) {
        return carsManagementService.addCar(car);
    }

    @GetMapping("/list")
    public List<Car> showAllUserCars (@RequestParam("id") String userId) {
        return carsManagementService.findByUser(userId);
    }

    @PatchMapping("/active/{id}")
    public Car updateActiveState(@PathVariable String id, @RequestBody Car car) throws CarException {
        return carsManagementService.updateActiveState(id, car);
    }
}
