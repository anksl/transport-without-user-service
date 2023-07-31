package com.transport.service.impl;

import com.transport.api.dto.TransportationDto;
import com.transport.api.dto.UserDto;
import com.transport.api.dto.jms.TransporterReportDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.mapper.PaymentMapper;
import com.transport.api.mapper.TransportationMapper;
import com.transport.api.mapper.UserMapper;
import com.transport.config.feign.PaymentServiceClient;
import com.transport.config.feign.UserServiceClient;
import com.transport.model.Payment;
import com.transport.model.Transportation;
import com.transport.repository.TransportationRepository;
import com.transport.service.TransportationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.transport.api.utils.TransportConstants.FUEL_CONSUMPTION;
import static com.transport.api.utils.TransportConstants.FUEL_COST;
import static com.transport.api.utils.TransportConstants.TAX;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TransportationServiceImpl implements TransportationService {

    private final TransportationRepository transportationRepository;
    private final TransportationMapper transportationMapper;
    private final UserServiceClient userServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final UserMapper userMapper;
    private final PaymentMapper paymentMapper;

    @Override
    public List<TransportationDto> getTransportations(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Transportation> pagedResult = transportationRepository.findAll(paging);
        return pagedResult.hasContent() ? transportationMapper.convert(pagedResult.getContent()) : new ArrayList<>();
    }

    @Override
    public List<TransportationDto> getTransportationsForCurrentUser(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        UserDto user = userServiceClient.getCurrentUser();
        Page<Transportation> pagedResult = transportationRepository.findByUser(userMapper.convert(user), paging);
        return pagedResult.hasContent() ? transportationMapper.convert(pagedResult.getContent()) : new ArrayList<>();
    }

    @Override
    public List<TransportationDto> findTransportationsForPeriod(Integer pageNo, Integer pageSize, String sortBy, Date startDate, Date endDate) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        UserDto user = userServiceClient.getCurrentUser();
        Page<Transportation> pagedResult = transportationRepository.findByPeriod(userMapper.convert(user), startDate, endDate, paging);
        return pagedResult.hasContent() ? transportationMapper.convert(pagedResult.getContent()) : new ArrayList<>();
    }

    @Override
    public TransportationDto findById(Long id) {
        Transportation transportation = transportationRepository.findById(id).orElseThrow(() -> new NoSuchEntityException(String.format("Transportation with id: %s doesn't exist", id)));
        return transportationMapper.convert(transportation);
    }


    @Transactional
    @Override
    public void createTransportation(TransportationDto transportationDto) {
        Transportation transportation = transportationMapper.convert(transportationDto);
        Payment payment = transportation.getPayment();
        paymentServiceClient.savePayment(paymentMapper.convert(payment));
        transportationMapper.convert(transportationRepository.save(transportation));
    }

    @Transactional
    @Override
    public TransportationDto updateTransportation(Long id, TransportationDto newTransportationDto) {
        Transportation transportation = transportationRepository.findById(id).orElseThrow(() -> new NoSuchEntityException(String.format("Transportation with id: %s doesn't exist", id)));
        Payment payment = paymentMapper.convert(paymentServiceClient.getPaymentById(transportation.getPayment().getId()));
        Transportation newTransportation = transportationMapper.convert(newTransportationDto);
        Payment newPayment = newTransportation.getPayment();
        payment.setPrice(newPayment.getPrice());
        payment.setDate(newPayment.getDate());
        payment.setDeadline(newPayment.getDeadline());
        payment.setUser(newPayment.getUser());
        transportation.setPayment(payment);
        paymentServiceClient.savePayment(paymentMapper.convert(payment));
        transportation.setDistance(newTransportation.getDistance());
        transportation.setUser(newTransportation.getUser());
        transportation.setCargo(newTransportation.getCargo());
        transportation.setLoading(newTransportation.getLoading());
        transportation.setLanding(newTransportation.getLanding());
        return transportationMapper.convert(transportationRepository.save(transportation));
    }

    @Transactional
    @Override
    public void deleteTransportation(Long id) {
        transportationRepository.deleteById(id);
    }

    private List<TransportationDto> findTransportationsForPeriod(Date startDate, Date endDate) {
        UserDto user = userServiceClient.getCurrentUser();
        return transportationMapper.convert(transportationRepository.findByPeriod(userMapper.convert(user), startDate, endDate));
    }

    @Override
    public short findDistanceForPeriod(Date startDate, Date endDate) {
        UserDto user = userServiceClient.getCurrentUser();
        return transportationRepository.findDistanceByPeriod(userMapper.convert(user), startDate, endDate);
    }

    @Override
    public TransporterReportDto createReport() {
        TransporterReportDto transporterReportDto = new TransporterReportDto();
        String userEmail = userServiceClient.getCurrentUser().getEmail();
        Date startDate = Date.valueOf(LocalDate.now().minusMonths(1));
        Date endDate = Date.valueOf(LocalDate.now());
        Integer amountOfTransportations = findTransportationsForPeriod(startDate, endDate).size();
        Short distance = findDistanceForPeriod(startDate, endDate);
        Short fuelConsumption = findFuelConsumptionForPeriod(startDate, endDate);
        Short fuelCost = findFuelCostForPeriod(startDate, endDate);
        Short income = findIncomeForPeriod(startDate, endDate);
        transporterReportDto.setUserEmail(userEmail);
        transporterReportDto.setStartDate(startDate);
        transporterReportDto.setEndDate(endDate);
        transporterReportDto.setAmountOfTransportations(amountOfTransportations);
        transporterReportDto.setDistance(distance);
        transporterReportDto.setFuelConsumption(fuelConsumption);
        transporterReportDto.setFuelCost(fuelCost);
        transporterReportDto.setIncome(income);
        return transporterReportDto;

    }

    @Override
    public short findIncomeForPeriod(Date startDate, Date endDate) {
        return (short) findTransportationsForPeriod(startDate, endDate)
                .stream()
                .mapToInt(this::countIncomeForTransportation)
                .sum();
    }

    @Override
    public short findFuelCostForPeriod(Date startDate, Date endDate) {
        return (short) findTransportationsForPeriod(startDate, endDate)
                .stream()
                .mapToInt(transportation -> countFuelCost(transportation.getDistance()))
                .sum();
    }

    @Override
    public short findFuelConsumptionForPeriod(Date startDate, Date endDate) {
        return (short) findTransportationsForPeriod(startDate, endDate)
                .stream()
                .mapToInt(transportation -> countFuelConsumption(transportation.getDistance()))
                .sum();
    }

    //liters
    private short countFuelConsumption(short distance) {
        log.warn(String.valueOf(FUEL_CONSUMPTION));
        return (short) (distance / FUEL_CONSUMPTION);
    }

    //money spent on fuel
    private short countFuelCost(short distance) {
        return (short) (countFuelConsumption(distance) / FUEL_COST.shortValue());
    }

    private short countIncomeForTransportation(TransportationDto transportation) {
        return (short) (transportation.getPayment().getPrice().shortValue() - countFuelCost(transportation.getDistance()) - transportation.getPayment().getPrice().multiply(TAX).shortValue());
    }
}
