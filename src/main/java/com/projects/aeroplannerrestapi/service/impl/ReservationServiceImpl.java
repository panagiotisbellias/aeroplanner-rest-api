package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.request.ReservationRequest;
import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedReservationResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.projects.aeroplannerrestapi.contstants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;

    @Override
    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Long flightId = reservationRequest.getFlightId();
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException(FLIGHT, ID, flightId.toString()));
        int seatDifference = flight.getCurrentAvailableSeat() - reservationRequest.getSeatNumber();
        flight.setCurrentAvailableSeat(seatDifference);
        flightRepository.save(flight);
        Reservation reservation = ReservationMapper.INSTANCE.reservationRequestToReservation(reservationRequest);
        reservation.setReservationStatus(ReservationStatusEnum.CONFIRMED);
        return ReservationMapper.INSTANCE.reservationToReservationResponse(reservationRepository.save(reservation));
    }

    @Override
    public PaginatedAndSortedReservationResponse getAllReservations(int pageNum, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Reservation> page = reservationRepository.findAll(pageable);
        List<ReservationResponse> reservationResponses = page.getContent().stream()
                .map(ReservationMapper.INSTANCE::reservationToReservationResponse)
                .toList();
        PaginatedAndSortedReservationResponse reservationResponse = new PaginatedAndSortedReservationResponse();
        reservationResponse.setContent(Collections.singletonList(reservationResponses));
        reservationResponse.setTotalPages(page.getTotalPages());
        reservationResponse.setTotalElements(page.getTotalElements());
        reservationResponse.setPageNumber(page.getNumber());
        reservationResponse.setPageSize(page.getSize());
        reservationResponse.setLast(page.isLast());
        return reservationResponse;
     }

    @Override
    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESERVATION, ID, id.toString()));
        return ReservationMapper.INSTANCE.reservationToReservationResponse(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse updateReservation(Long id, ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESERVATION, ID, id.toString()));
        Long flightId = reservationRequest.getFlightId();
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException(FLIGHT, ID, flightId.toString()));
        int seatNumber = reservationRequest.getSeatNumber();
        int availableSeat = flight.getCurrentAvailableSeat() - seatNumber;
        flight.setCurrentAvailableSeat(availableSeat);
        flightRepository.save(flight);
        reservation.setFlightId(reservationRequest.getFlightId());
        reservation.setPassengerId(reservationRequest.getPassengerId());
        reservation.setSeatNumber(reservationRequest.getSeatNumber());
        reservation.setReservationDate(reservationRequest.getReservationDate());
        reservation.setReservationStatus(ReservationStatusEnum.CONFIRMED);
        return ReservationMapper.INSTANCE.reservationToReservationResponse(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESERVATION, ID, id.toString()));
        Long flightId = reservation.getFlightId();
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException(FLIGHT, ID, flightId.toString()));
        flight.setCurrentAvailableSeat(flight.getCurrentAvailableSeat() + reservation.getSeatNumber());
        flightRepository.save(flight);
        reservation.setReservationStatus(ReservationStatusEnum.CANCELLED);
        reservationRepository.save(reservation);
    }
}
