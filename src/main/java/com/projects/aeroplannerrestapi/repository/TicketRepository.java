package com.projects.aeroplannerrestapi.repository;

import com.projects.aeroplannerrestapi.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
