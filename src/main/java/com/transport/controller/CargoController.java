package com.transport.controller;

import com.transport.api.dto.CargoDto;
import com.transport.service.CargoService;
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

import java.util.List;

@RestController
@RequestMapping("/api/cargos")
@RequiredArgsConstructor
public class CargoController {
    private final CargoService cargoService;

    @GetMapping
    public ResponseEntity<List<CargoDto>> getAllCargos(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<CargoDto> list = cargoService.getCargos(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<List<CargoDto>> getCargosByName(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam String name) {
        List<CargoDto> list = cargoService.getCargosByName(pageNo, pageSize, sortBy, name);

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDto> getCargo(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(cargoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCargo(@Validated @RequestBody CargoDto cargo) {
        cargoService.createCargo(cargo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDto> updateCargo(@PathVariable(value = "id") Long id, @Validated @RequestBody CargoDto newCargo) {
        return ResponseEntity.ok(cargoService.updateCargo(id, newCargo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable(value = "id") Long id) {
        cargoService.deleteCargo(id);
        return ResponseEntity.noContent().build();
    }
}
