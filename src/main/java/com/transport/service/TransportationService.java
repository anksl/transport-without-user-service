package com.transport.service;

import com.transport.api.dto.TransportationDto;
import com.transport.model.Email;

import java.sql.Date;
import java.util.List;

public interface TransportationService {
    List<TransportationDto> getTransportations(Integer pageNo, Integer pageSize, String sortBy);

    List<TransportationDto> getTransportationsForCurrentUser(Integer pageNo, Integer pageSize, String sortBy);

    List<TransportationDto> findTransportationsForPeriod(Integer pageNo, Integer pageSize, String sortBy, Date startDate, Date endDate);

    TransportationDto findById(Long id);

    void createTransportation(TransportationDto transportation);

    TransportationDto updateTransportation(Long id, TransportationDto newTransportation);

    void deleteTransportation(Long id);

    short findIncomeForPeriod(Date startDate, Date endDate);

    short findFuelCostForPeriod(Date startDate, Date endDate);

    short findFuelConsumptionForPeriod(Date startDate, Date endDate);

    short findDistanceForPeriod(Date startDate, Date endDate);

    Email createReport();
}
