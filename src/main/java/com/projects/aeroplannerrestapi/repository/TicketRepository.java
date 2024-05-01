package com.projects.aeroplannerrestapi.repository;

import com.projects.aeroplannerrestapi.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByReservationId(String reservationId);
}
