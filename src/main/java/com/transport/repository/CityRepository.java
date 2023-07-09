package com.transport.repository;

import com.transport.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsCityByName(String name);
}
