package com.transport.api.mapper;

import com.transport.api.dto.DeliveryDto;
import com.transport.model.Delivery;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    Delivery convert(DeliveryDto deliveryDto);

    DeliveryDto convert(Delivery delivery);

    List<DeliveryDto> convert(List<Delivery> deliveries);
}
