package com.transport.api.mapper;

import com.transport.api.dto.CargoDto;
import com.transport.model.Cargo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CargoMapper {
    Cargo convert(CargoDto cargoDto);

    CargoDto convert(Cargo cargo);

    List<CargoDto> convert(List<Cargo> cargos);
}
