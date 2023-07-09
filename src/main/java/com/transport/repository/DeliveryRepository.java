package com.transport.repository;

import com.transport.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Page<Delivery> findByDate(Date date, Pageable pageable);
}
