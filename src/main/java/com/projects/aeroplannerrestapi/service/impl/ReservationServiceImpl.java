package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.ReservationDto;
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
    public ReservationDto createReservation(ReservationDto reservationDto) {
        Long flightId = reservationDto.getFlightId();
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId.toString()));
        int seatDifference = flight.getSeatAvailability() - reservationDto.getSeatNumber();
        flight.setCurrentAvailableSeat(seatDifference);
        flightRepository.save(flight);
        Reservation reservation = ReservationMapper.INSTANCE.reservationDtoToReservation(reservationDto);
        return ReservationMapper.INSTANCE.reservationToReservationDto(reservationRepository.save(reservation));
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper.INSTANCE::reservationToReservationDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDto getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id.toString()));
        return ReservationMapper.INSTANCE.reservationToReservationDto(reservation);
    }

    @Override
    @Transactional
    public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id.toString()));
        Long flightId = reservationDto.getFlightId();
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId.toString()));
        int seatNumber = reservationDto.getSeatNumber();
        int availableSeat = flight.getSeatAvailability() - seatNumber;
        flight.setCurrentAvailableSeat(availableSeat);
        flightRepository.save(flight);
        reservation.setFlightId(reservationDto.getFlightId());
        reservation.setPassengerId(reservationDto.getPassengerId());
        reservation.setSeatNumber(reservationDto.getSeatNumber());
        reservation.setReservationDate(reservationDto.getReservationDate());
        reservation.setReservationStatus(reservationDto.getReservationStatus());
        return ReservationMapper.INSTANCE.reservationToReservationDto(reservationRepository.save(reservation));
    }

    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id.toString()));
        reservation.setReservationStatus(ReservationStatusEnum.CANCELLED);
    }
}
