package com.transport.service;

import com.transport.api.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddresses(Integer pageNo, Integer pageSize, String sortBy);

    List<AddressDto> getAddressesByCountry(Integer pageNo, Integer pageSize, String sortBy, String countryName);

    List<AddressDto> getAddressesByCity(Integer pageNo, Integer pageSize, String sortBy, String countryName);

    AddressDto findById(Long id);

    void createAddress(AddressDto addressDto);

    AddressDto updateAddress(Long id, AddressDto newAddress);

    void deleteAddress(Long id);
}
