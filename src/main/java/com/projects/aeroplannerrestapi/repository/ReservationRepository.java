package com.projects.aeroplannerrestapi.repository;

import com.projects.aeroplannerrestapi.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
