package com.transport.controller;

import com.transport.api.dto.DeliveryDto;
import com.transport.service.DeliveryService;
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
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<DeliveryDto>> getAllDeliveries(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<DeliveryDto> list = deliveryService.getDeliveries(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesByDate(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "Date.valueOf(LocalDate.now())") Date date) {
        List<DeliveryDto> list = deliveryService.getDeliveriesByDate(pageNo, pageSize, sortBy, date);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDto> getDelivery(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(deliveryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createDelivery(@Validated @RequestBody DeliveryDto delivery) {
        deliveryService.createDelivery(delivery);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDto> updateDelivery(@PathVariable(value = "id") Long id, @Validated @RequestBody DeliveryDto newDelivery) {
        return ResponseEntity.ok(deliveryService.updateDelivery(id, newDelivery));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable(value = "id") Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }
}
