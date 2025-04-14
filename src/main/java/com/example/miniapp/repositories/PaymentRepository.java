package com.example.miniapp.repositories;

import com.example.miniapp.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Query to find payments by trip ID
    List<Payment> findByTripId(Long tripId);

    // Query to find payments above a specified amount threshold
    List<Payment> findByAmountGreaterThan(Double amount);
}
