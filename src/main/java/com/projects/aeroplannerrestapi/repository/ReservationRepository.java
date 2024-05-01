package com.projects.aeroplannerrestapi.repository;

import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByFlightIdAndPassengerId(Long flightId, Long passengerId);

    List<Reservation> findByReservationStatus (ReservationStatusEnum reservationStatusEnum);
}
