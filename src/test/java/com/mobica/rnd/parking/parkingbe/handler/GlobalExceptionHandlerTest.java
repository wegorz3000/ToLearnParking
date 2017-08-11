package com.mobica.rnd.parking.parkingbe.handler;

import com.mobica.rnd.parking.parkingbe.exception.CarException;
import com.mobica.rnd.parking.parkingbe.exception.MarkAvailableSlotsException;
import com.mobica.rnd.parking.parkingbe.exception.ParkingNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.codec.DecodingException;
import org.springframework.data.mapping.MappingException;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GlobalExceptionHandlerTest {
    @Mock
    private GlobalExceptionHandler globalExceptionHandler;
    @Mock
    private DecodingException decodingException;
    @Mock
    private Map<String, String> map;
    @Mock
    private WebExchangeBindException webExchangeBindException;
    @Mock
    private IllegalStateException illegalStateException;
    @Mock
    private ParkingNotFoundException parkingNotFoundException;
    @Mock
    private MappingException mappingException;
    @Mock
    private TypeMismatchException typeMismatchException;
    @Mock
    private MarkAvailableSlotsException markAvailableSlotsException;
    @Mock
    private CarException carException;

    @Test
    public void parkingNotFoundExceptionTest() {
        Mockito.when(globalExceptionHandler.handleParkingNotFoundException(parkingNotFoundException)).thenReturn(map);
        assertEquals(map, globalExceptionHandler.handleParkingNotFoundException(parkingNotFoundException));
        Mockito.verify(globalExceptionHandler, Mockito.times(1)).handleParkingNotFoundException(parkingNotFoundException);
    }

    @Test
    public void illegalStateExceptionHandlerTest() {
        Mockito.when(globalExceptionHandler.handleIllegalStateDecodingOrMappingException(illegalStateException)).thenReturn(map);
        assertEquals(map, globalExceptionHandler.handleIllegalStateDecodingOrMappingException(illegalStateException));
        Mockito.verify(globalExceptionHandler, Mockito.times(1)).handleIllegalStateDecodingOrMappingException(illegalStateException);
    }

    @Test
    public void decodingExceptionHandlerTest() {
        Mockito.when(globalExceptionHandler.handleIllegalStateDecodingOrMappingException(decodingException)).thenReturn(map);
        assertEquals(map, globalExceptionHandler.handleIllegalStateDecodingOrMappingException(decodingException));
        Mockito.verify(globalExceptionHandler, Mockito.times(1)).handleIllegalStateDecodingOrMappingException(decodingException);
    }

    @Test
    public void mappingExceptionHandlerTest() {
        Mockito.when(globalExceptionHandler.handleIllegalStateDecodingOrMappingException(mappingException)).thenReturn(map);
        assertEquals(map, globalExceptionHandler.handleIllegalStateDecodingOrMappingException(mappingException));
        Mockito.verify(globalExceptionHandler, Mockito.times(1)).handleIllegalStateDecodingOrMappingException(mappingException);
    }

    @Test
    public void webExchangeBindExceptionHandlerTest() {
        Mockito.when(globalExceptionHandler.handleWebExchangeBindException(webExchangeBindException)).thenReturn(map);
        assertEquals(map, globalExceptionHandler.handleWebExchangeBindException(webExchangeBindException));
        Mockito.verify(globalExceptionHandler, Mockito.times(1)).handleWebExchangeBindException(webExchangeBindException);
    }

    @Test
    public void typeMismatchExceptionHandlerTest() {
        Mockito.when(globalExceptionHandler.handleTypeMismatchException(typeMismatchException)).thenReturn(map);
        assertEquals(map, globalExceptionHandler.handleTypeMismatchException(typeMismatchException));
        Mockito.verify(globalExceptionHandler, Mockito.times(1)).handleTypeMismatchException(typeMismatchException);
    }

    @Test
    public void markAvailableSlotsExceptionHandlerTest() {
        Mockito.when(globalExceptionHandler.handleMarkAvailableSlotsException(markAvailableSlotsException)).thenReturn(map);
        assertEquals(map, globalExceptionHandler.handleMarkAvailableSlotsException(markAvailableSlotsException));
        Mockito.verify(globalExceptionHandler, Mockito.times(1)).handleMarkAvailableSlotsException(markAvailableSlotsException);
    }

    @Test
    public void carExceptionHandlerTest() {
        Mockito.when(globalExceptionHandler.handleCarException(carException)).thenReturn(map);
        assertEquals(map, globalExceptionHandler.handleCarException(carException));
        Mockito.verify(globalExceptionHandler, Mockito.times(1)).handleCarException(carException);
    }
}