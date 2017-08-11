package com.mobica.rnd.parking.parkingbe.controller;

import com.mobica.rnd.parking.parkingbe.annotation.InMonthsLimit;
import com.mobica.rnd.parking.parkingbe.annotation.NotPast;
import com.mobica.rnd.parking.parkingbe.exception.MarkAvailableSlotsException;
import com.mobica.rnd.parking.parkingbe.model.ParkingPlace;
import com.mobica.rnd.parking.parkingbe.model.Reservation;
import com.mobica.rnd.parking.parkingbe.service.EmptyReservationService;
import com.mobica.rnd.parking.parkingbe.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
@Validated
public class ReservationController {
    private EmptyReservationService emptyReservationService;
    private ReservationServiceImpl reservationService;

    @Autowired
    public ReservationController(EmptyReservationService emptyReservationService, ReservationServiceImpl reservationService) {
        this.emptyReservationService = emptyReservationService;
        this.reservationService = reservationService;
    }

    @PostMapping("/available")
    public Map<String, String> markAvailableParkingPlaces(@RequestBody List<ParkingPlace> parkingPlaces,
                                                          @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotPast Optional<LocalDate> startDate,
                                                          @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotPast @InMonthsLimit Optional<LocalDate> endDate) throws MarkAvailableSlotsException {
        return emptyReservationService.markAvailableParkingPlaces(parkingPlaces, startDate.get(), endDate.get());
    }

    @PostMapping
    public ResponseEntity<?> reserve(@RequestBody @Valid Reservation reservation) {
        return reservationService.reserveParkingPlace(reservation);
    }
}
