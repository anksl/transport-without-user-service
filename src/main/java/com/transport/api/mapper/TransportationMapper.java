package com.transport.api.mapper;

import com.transport.api.dto.TransportationDto;
import com.transport.model.Transportation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransportationMapper {
    Transportation convert(TransportationDto transportationDto);

    TransportationDto convert(Transportation transportation);

    List<TransportationDto> convert(List<Transportation> transportations);
}