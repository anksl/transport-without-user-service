package com.transport.repository;

import com.transport.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsCountryByName(String name);
}
