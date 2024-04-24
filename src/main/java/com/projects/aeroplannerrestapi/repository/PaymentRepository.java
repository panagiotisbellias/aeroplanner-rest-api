package com.projects.aeroplannerrestapi.repository;

import com.projects.aeroplannerrestapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
