package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.FlightDto;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.mapper.FlightMapper;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public FlightDto createFlight(FlightDto flightDto) {
        Flight flight = FlightMapper.INSTANCE.flightDtoToFlight(flightDto);
        flight.setCurrentAvailableSeat(flightDto.getSeatAvailability());
        flight.setDuration(Duration.between(LocalDateTime.parse(flightDto.getDepartureTime()),
                LocalDateTime.parse(flightDto.getArrivalTime())));
        Flight savedFlight = flightRepository.save(flight);
        return FlightMapper.INSTANCE.flightToFlightDto(savedFlight);
    }

    @Override
    public List<FlightDto> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(FlightMapper.INSTANCE::flightToFlightDto)
                .collect(Collectors.toList());
    }

    @Override
    public FlightDto getFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id.toString()));
        return FlightMapper.INSTANCE.flightToFlightDto(flight);
    }

    @Override
    public FlightDto updateFlight(Long id, FlightDto flightDto) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id.toString()));
        flight.setFlightNumber(flightDto.getFlightNumber());
        flight.setAirline(flightDto.getAirline());
        flight.setAircraftType(flightDto.getAircraftType());
        flight.setDuration(Duration.between(LocalDateTime.parse(flightDto.getDepartureTime()),
                LocalDateTime.parse(flightDto.getArrivalTime())));
        flight.setPrice(flightDto.getPrice());
        flight.setStatus(flightDto.getStatus());
        flight.setArrivalTime(flightDto.getArrivalTime());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setSeatAvailability(flightDto.getSeatAvailability());
        return FlightMapper.INSTANCE.flightToFlightDto(flightRepository.save(flight));
    }

    @Override
    public void deleteFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id.toString()));
        flightRepository.delete(flight);
    }
}
