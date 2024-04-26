package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.ReservationRequest;
import com.projects.aeroplannerrestapi.dto.response.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse createReservation(ReservationRequest reservationRequest);

    List<ReservationResponse> getAllReservations();

    ReservationResponse getReservation(Long id);

    ReservationResponse updateReservation(Long id, ReservationRequest reservationRequest);

    void cancelReservation(Long id);
}
