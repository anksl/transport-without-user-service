package com.transport.repository;

import com.transport.model.Transportation;
import com.transport.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    Page<Transportation> findByUser(User user, Pageable pageable);

    @Query("from Transportation t WHERE t.user = :user and t.loading.date BETWEEN :startDate AND :endDate")
    Page<Transportation> findByPeriod(@Param("user") User user, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    @Query("from Transportation t WHERE t.user = :user and t.loading.date BETWEEN :startDate AND :endDate")
    List<Transportation> findByPeriod(@Param("user") User user, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT SUM(t.distance) from Transportation t WHERE t.user = :user and t.loading.date BETWEEN :startDate AND :endDate")
    short findDistanceByPeriod(@Param("user") User user, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
