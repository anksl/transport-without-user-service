package com.transport.service.impl;

import com.transport.api.dto.DeliveryDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.mapper.DeliveryMapper;
import com.transport.model.Address;
import com.transport.model.Delivery;
import com.transport.repository.AddressRepository;
import com.transport.repository.DeliveryRepository;
import com.transport.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final AddressRepository addressRepository;
    private final DeliveryMapper deliveryMapper;

    @Transactional
    @Override
    public void createDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = deliveryMapper.convert(deliveryDto);
        addressRepository.save(delivery.getAddress());
        deliveryMapper.convert(deliveryRepository.save(delivery));
    }

    @Override
    public List<DeliveryDto> getDeliveries(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Delivery> pagedResult = deliveryRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return deliveryMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<DeliveryDto> getDeliveriesByDate(Integer pageNo, Integer pageSize, String sortBy, Date date) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Delivery> pagedResult = deliveryRepository.findByDate(date, paging);
        if (pagedResult.hasContent()) {
            return deliveryMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public DeliveryDto findById(Long id) {
        return deliveryMapper.convert(deliveryRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Delivery with id: %s doesn't exist", id))));
    }

    @Transactional
    @Override
    public DeliveryDto updateDelivery(Long id, DeliveryDto newDeliveryDto) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Delivery with id: %s doesn't exist", id)));
        Address address = addressRepository.findById(newDeliveryDto.getAddress().getId())
                .orElseThrow(() -> new NoSuchEntityException(String.format("Address with id: %s doesn't exist", newDeliveryDto.getAddress().getId())));
        Delivery newDelivery = deliveryMapper.convert(newDeliveryDto);
        delivery.setDate(newDelivery.getDate());
        delivery.setLoadType(newDelivery.getLoadType());
        Address newAddress = newDelivery.getAddress();
        address.setApartment(newAddress.getApartment());
        address.setHouse(newAddress.getHouse());
        address.setStreet(newAddress.getStreet());
        address.setPhoneNumber(newAddress.getPhoneNumber());
        address.setCity(newAddress.getCity());
        delivery.setAddress(address);
        addressRepository.save(address);
        return deliveryMapper.convert(deliveryRepository.save(delivery));
    }

    @Transactional
    @Override
    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }
}
