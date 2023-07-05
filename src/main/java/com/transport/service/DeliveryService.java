package com.transport.service;

import com.transport.api.dto.DeliveryDto;

import java.sql.Date;
import java.util.List;

public interface DeliveryService {
    List<DeliveryDto> getDeliveries(Integer pageNo, Integer pageSize, String sortBy);

    List<DeliveryDto> getDeliveriesByDate(Integer pageNo, Integer pageSize, String sortBy, Date date);

    DeliveryDto findById(Long id);

    void createDelivery(DeliveryDto deliveryDto);

    DeliveryDto updateDelivery(Long id, DeliveryDto newDelivery);

    void deleteDelivery(Long id);
}
