package com.mobica.rnd.parking.parkingbe.service;

import com.mobica.rnd.parking.parkingbe.exception.CarException;
import com.mobica.rnd.parking.parkingbe.model.Car;
import com.mobica.rnd.parking.parkingbe.repository.CarRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CarsManagementServiceTest {
    @Spy
    private CarsManagementService carsManagementService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private Car car;
    private Car c;

    @Before
    public void setup() {
        c = new Car(RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                RandomStringUtils.randomAlphanumeric(1),
                true);
        ReflectionTestUtils.setField(carsManagementService, "carRepository", carRepository);
    }

    @Test
    public void addCarTest() {
        Mockito.when(carRepository.insert(car)).thenReturn(car);
        assertEquals(car, carsManagementService.addCar(car));
        Mockito.verify(carRepository, Mockito.times(1)).insert(car);
    }

    @Test
    public void findByUserTest() {
        String id = RandomStringUtils.randomAlphanumeric(5);
        List<Car> notEmptyList = new ArrayList();
        notEmptyList.add(car);
        Mockito.when(carRepository.findByUserId(id)).thenReturn(notEmptyList);
        List<Car> emptyList = carsManagementService.findByUser(id);
        Assert.notEmpty(emptyList);
        Mockito.verify(carRepository, Mockito.times(1)).findByUserId(id);
    }

    @Test
    public void updateActiveStatePositiveTest() throws CarException {
        Mockito.doReturn(c).when(carsManagementService).findCarById(any(String.class));
        Mockito.when(carRepository.save(c)).thenReturn(c);
        assertEquals(c, carsManagementService.updateActiveState(RandomStringUtils.randomAlphanumeric(1), c));
    }

    @Test(expected = CarException.class)
    public void updateActiveStateNullParameterTest() throws CarException {
        c.setActiveState(null);
        carsManagementService.updateActiveState(RandomStringUtils.randomAlphanumeric(1), c);
    }

    @Test(expected = CarException.class)
    public void updateActiveStateCarNotFoundTest() throws CarException {
        Mockito.doThrow(CarException.class).when(carsManagementService).findCarById(any(String.class));
        carsManagementService.updateActiveState(RandomStringUtils.randomAlphanumeric(1), c);
    }
}
