package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.ReservationRequest;
import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedReservationResponse;
import com.projects.aeroplannerrestapi.dto.response.ReservationResponse;

public interface ReservationService {

    ReservationResponse createReservation(ReservationRequest reservationRequest);

    PaginatedAndSortedReservationResponse getAllReservations(int pageNum, int pageSize, String sortBy, String sortDir);

    ReservationResponse getReservation(Long id);

    ReservationResponse updateReservation(Long id, ReservationRequest reservationRequest);

    void cancelReservation(Long id);
}
