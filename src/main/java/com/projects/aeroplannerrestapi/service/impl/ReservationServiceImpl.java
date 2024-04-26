package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.request.ReservationRequest;
import com.projects.aeroplannerrestapi.dto.response.ReservationResponse;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.mapper.ReservationMapper;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;

    @Override
    @Transactional
    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Long flightId = reservationRequest.getFlightId();
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId.toString()));
        int seatDifference = flight.getSeatAvailability() - reservationRequest.getSeatNumber();
        flight.setCurrentAvailableSeat(seatDifference);
        flightRepository.save(flight);
        Reservation reservation = ReservationMapper.INSTANCE.reservationRequestToReservation(reservationRequest);
        return ReservationMapper.INSTANCE.reservationToReservationResponse(reservationRepository.save(reservation));
    }

    @Override
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper.INSTANCE::reservationToReservationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id.toString()));
        return ReservationMapper.INSTANCE.reservationToReservationResponse(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse updateReservation(Long id, ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id.toString()));
        Long flightId = reservationRequest.getFlightId();
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId.toString()));
        int seatNumber = reservationRequest.getSeatNumber();
        int availableSeat = flight.getSeatAvailability() - seatNumber;
        flight.setCurrentAvailableSeat(availableSeat);
        flightRepository.save(flight);
        reservation.setFlightId(reservationRequest.getFlightId());
        reservation.setPassengerId(reservationRequest.getPassengerId());
        reservation.setSeatNumber(reservationRequest.getSeatNumber());
        reservation.setReservationDate(reservationRequest.getReservationDate());
        reservation.setReservationStatus(reservationRequest.getReservationStatus());
        return ReservationMapper.INSTANCE.reservationToReservationResponse(reservationRepository.save(reservation));
    }

    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id.toString()));
        reservation.setReservationStatus(ReservationStatusEnum.CANCELLED);
    }
}
