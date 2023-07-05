package com.transport.repository;

import com.transport.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findByCity_Country_Name(String countryName, Pageable pageable);

    Page<Address> findByCityName(String cityName, Pageable pageable);
}
