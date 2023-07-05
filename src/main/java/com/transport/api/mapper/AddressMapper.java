package com.transport.api.mapper;

import com.transport.api.dto.AddressDto;
import com.transport.model.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address convert(AddressDto addressDto);

    AddressDto convert(Address address);

    List<AddressDto> convert(List<Address> addresses);
}
