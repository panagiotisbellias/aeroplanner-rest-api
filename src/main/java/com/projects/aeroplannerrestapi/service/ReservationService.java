package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.ReservationDto;

import java.util.List;

public interface ReservationService {

    ReservationDto createReservation(ReservationDto reservationDto);

    List<ReservationDto> getAllReservations();

    ReservationDto getReservation(Long id);

    ReservationDto updateReservation(Long id, ReservationDto reservationDto);

    void deleteReservation(Long id);
}
