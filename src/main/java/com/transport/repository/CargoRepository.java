package com.transport.repository;

import com.transport.model.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Page<Cargo> findByName(String name, Pageable pageable);
}
