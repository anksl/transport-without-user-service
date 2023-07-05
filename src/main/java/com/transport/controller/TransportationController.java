package com.transport.controller;

import com.transport.api.dto.TransportationDto;
import com.transport.service.TransportationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/transportations")
@RequiredArgsConstructor
public class TransportationController {
    private final TransportationService transportationService;

    @GetMapping
    public ResponseEntity<List<TransportationDto>> getAllTransportations(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<TransportationDto> list = transportationService.getTransportations(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{transportationId}")
    public ResponseEntity<TransportationDto> getTransportationsById(@PathVariable(value = "transportationId") Long id) {
        return ResponseEntity.ok(transportationService.findById(id));
    }

    @GetMapping("/current")
    public ResponseEntity<List<TransportationDto>> getTransportationsForCurrentUser(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<TransportationDto> list = transportationService.getTransportationsForCurrentUser(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/period")
    public ResponseEntity<List<TransportationDto>> getTransportationsByPeriodForCurrentUser(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        List<TransportationDto> list = transportationService.findTransportationsForPeriod(pageNo, pageSize, sortBy, startDate, endDate);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createTransportation(@Validated @RequestBody TransportationDto transportationDto) {
        transportationService.createTransportation(transportationDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportationDto> updateTransportation(@PathVariable(value = "id") Long id, @Validated @RequestBody TransportationDto newTransportation) {
        return ResponseEntity.ok(transportationService.updateTransportation(id, newTransportation));
    }

    @DeleteMapping("/{transportationId}")
    public ResponseEntity<Void> deleteTransportation(@PathVariable(value = "transportationId") Long id) {
        transportationService.deleteTransportation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/income")
    public ResponseEntity<Short> getIncomeForPeriodForCurrentUser(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        Short income = transportationService.findIncomeForPeriod(startDate, endDate);

        return new ResponseEntity<>(income, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/fuelCost")
    public ResponseEntity<Short> getFuelCostForPeriodForCurrentUser(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        Short income = transportationService.findFuelCostForPeriod(startDate, endDate);

        return new ResponseEntity<>(income, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/fuelConsumption")
    public ResponseEntity<Short> getFuelConsumptionForPeriodForCurrentUser(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        Short income = transportationService.findFuelConsumptionForPeriod(startDate, endDate);

        return new ResponseEntity<>(income, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/distance")
    public ResponseEntity<Short> getDistanceForPeriodForCurrentUser(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        Short income = transportationService.findDistanceForPeriod(startDate, endDate);

        return new ResponseEntity<>(income, new HttpHeaders(), HttpStatus.OK);
    }
}
