package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.request.FlightRequest;
import com.projects.aeroplannerrestapi.dto.response.FlightResponse;
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
    public FlightResponse createFlight(FlightRequest flightRequest) {
        Flight flight = FlightMapper.INSTANCE.flightRequestToFlight(flightRequest);
        flight.setCurrentAvailableSeat(flightRequest.getSeatAvailability());
        flight.setDuration(Duration.between(LocalDateTime.parse(flightRequest.getDepartureTime()),
                LocalDateTime.parse(flightRequest.getArrivalTime())));
        Flight savedFlight = flightRepository.save(flight);
        return FlightMapper.INSTANCE.flightToFlightResponse(savedFlight);
    }

    @Override
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(FlightMapper.INSTANCE::flightToFlightResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FlightResponse getFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id.toString()));
        return FlightMapper.INSTANCE.flightToFlightResponse(flight);
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id.toString()));
        flight.setFlightNumber(flightRequest.getFlightNumber());
        flight.setAirline(flightRequest.getAirline());
        flight.setAircraftType(flightRequest.getAircraftType());
        flight.setDuration(Duration.between(LocalDateTime.parse(flightRequest.getDepartureTime()),
                LocalDateTime.parse(flightRequest.getArrivalTime())));
        flight.setPrice(flightRequest.getPrice());
        flight.setStatus(flightRequest.getStatus());
        flight.setArrivalTime(flightRequest.getArrivalTime());
        flight.setDepartureTime(flightRequest.getDepartureTime());
        flight.setSeatAvailability(flightRequest.getSeatAvailability());
        return FlightMapper.INSTANCE.flightToFlightResponse(flightRepository.save(flight));
    }

    @Override
    public void deleteFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", id.toString()));
        flightRepository.delete(flight);
    }
}
