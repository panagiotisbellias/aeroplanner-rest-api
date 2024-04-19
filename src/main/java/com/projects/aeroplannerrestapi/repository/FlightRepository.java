package com.projects.aeroplannerrestapi.repository;

import com.projects.aeroplannerrestapi.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
