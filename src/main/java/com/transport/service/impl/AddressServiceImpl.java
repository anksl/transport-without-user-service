package com.transport.service.impl;

import com.transport.api.dto.AddressDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.mapper.AddressMapper;
import com.transport.model.Address;
import com.transport.repository.AddressRepository;
import com.transport.repository.CityRepository;
import com.transport.repository.CountryRepository;
import com.transport.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AddressMapper addressMapper;

    @Override
    public List<AddressDto> getAddresses(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Address> pagedResult = addressRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return addressMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<AddressDto> getAddressesByCountry(Integer pageNo, Integer pageSize, String sortBy, String countryName) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Address> pagedResult = addressRepository.findByCity_Country_Name(countryName, paging);
        if (pagedResult.hasContent()) {
            return addressMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<AddressDto> getAddressesByCity(Integer pageNo, Integer pageSize, String sortBy, String cityName) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Address> pagedResult = addressRepository.findByCityName(cityName, paging);
        if (pagedResult.hasContent()) {
            return addressMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public AddressDto findById(Long id) {
        return addressMapper.convert(addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Address with id: %s doesn't exist", id))));
    }

    @Transactional
    @Override
    public void createAddress(AddressDto addressDto) {
        Address address = addressMapper.convert(addressDto);
        checkCityAndCountryForExistence(address);
        addressRepository.save(address);
    }

    @Transactional
    @Override
    public AddressDto updateAddress(Long id, AddressDto newAddressDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Address with id: %s doesn't exist", id)));
        Address newAddress = addressMapper.convert(newAddressDto);
        address.setApartment(newAddress.getApartment());
        address.setHouse(newAddress.getHouse());
        address.setStreet(newAddress.getStreet());
        address.setPhoneNumber(newAddress.getPhoneNumber());
        checkCityAndCountryForExistence(address);
        address.setCity(newAddress.getCity());
        return addressMapper.convert(addressRepository.save(address));
    }

    private void checkCityAndCountryForExistence(Address address) {
        if (countryRepository.existsCountryByName(address.getCity().getCountry().getName()) && !cityRepository.existsCityByName(address.getCity().getName())) {
            cityRepository.save(address.getCity());
        } else if (!countryRepository.existsCountryByName(address.getCity().getCountry().getName())) {
            countryRepository.save(address.getCity().getCountry());
            cityRepository.save(address.getCity());
        }
    }

    @Transactional
    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
