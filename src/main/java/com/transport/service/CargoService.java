package com.transport.service;

import com.transport.api.dto.CargoDto;

import java.util.List;

public interface CargoService {
    List<CargoDto> getCargos(Integer pageNo, Integer pageSize, String sortBy);

    List<CargoDto> getCargosByName(Integer pageNo, Integer pageSize, String sortBy, String name);

    CargoDto findById(Long id);

    void createCargo(CargoDto cargo);

    CargoDto updateCargo(Long id, CargoDto newCargo);

    void deleteCargo(Long id);
}
