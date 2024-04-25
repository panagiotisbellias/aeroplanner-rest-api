package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.FlightRequest;
import com.projects.aeroplannerrestapi.dto.response.FlightResponse;

import java.util.List;

public interface FlightService {

    FlightResponse createFlight(FlightRequest flightDto);

    List<FlightResponse> getAllFlights();

    FlightResponse getFlight(Long id);

    FlightResponse updateFlight(Long id, FlightRequest flightRequest);

    void deleteFlight(Long id);
}
