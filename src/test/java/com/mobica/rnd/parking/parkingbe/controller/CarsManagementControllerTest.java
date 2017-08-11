package com.mobica.rnd.parking.parkingbe.controller;

import com.mobica.rnd.parking.parkingbe.exception.CarException;
import com.mobica.rnd.parking.parkingbe.model.Car;
import com.mobica.rnd.parking.parkingbe.service.CarsManagementService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CarsManagementControllerTest {
    @InjectMocks
    private CarsManagementController carsManagementController;

    @Mock
    private CarsManagementService carsManagementService;

    @Mock
    private Car car;

    @Test
    public void addCarTest() {
        Mockito.when(carsManagementService.addCar(car)).thenReturn(car);
        assertEquals(carsManagementController.addCar(car), car);
        Mockito.verify(carsManagementService, Mockito.times(1)).addCar(car);
    }

    @Test
    public void showAllUserCars_Test() {
        String id = RandomStringUtils.randomAlphanumeric(5);
        List<Car> notEmptyList = new ArrayList();
        notEmptyList.add(car);
        Mockito.when(carsManagementService.findByUser(id)).thenReturn(notEmptyList);
        List<Car> emptyList = carsManagementService.findByUser(id);
        Assert.notEmpty(emptyList);
        Mockito.verify(carsManagementService, Mockito.times(1)).findByUser(id);
    }
    @Test
    public void showUserCarList_WithAnyCarAdded_Test() {
        String id = RandomStringUtils.randomAlphanumeric(5);
        List<Car> emptyList = carsManagementService.findByUser(id);
        assertEquals(emptyList.isEmpty(), true);
        Mockito.verify(carsManagementService, Mockito.times(1)).findByUser(id);
    }

    @Test
    public void updateActiveStateTest() throws CarException {
        String id = RandomStringUtils.randomAlphanumeric(1);
        Mockito.doReturn(car).when(carsManagementService).updateActiveState(id, car);
        assertEquals(car, carsManagementController.updateActiveState(id, car));
        Mockito.verify(carsManagementService, Mockito.times(1)).updateActiveState(id, car);
    }

}
