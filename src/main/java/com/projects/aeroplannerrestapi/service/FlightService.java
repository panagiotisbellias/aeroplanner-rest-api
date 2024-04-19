package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.FlightDto;

import java.util.List;

public interface FlightService {

    FlightDto createFlight(FlightDto flightDto);

    List<FlightDto> getAllFlights();

    FlightDto getFlight(Long id);

    FlightDto updateFlight(Long id, FlightDto flightDto);

    void deleteFlight(Long id);
}
