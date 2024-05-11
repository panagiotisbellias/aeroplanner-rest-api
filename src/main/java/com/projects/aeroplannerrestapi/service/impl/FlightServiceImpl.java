package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.request.FlightRequest;
import com.projects.aeroplannerrestapi.dto.response.FlightResponse;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.mapper.FlightMapper;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.FLIGHT;
import static com.projects.aeroplannerrestapi.constants.ErrorMessage.ID;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private static final Log LOG = LogFactory.getLog(FlightServiceImpl.class);

    private final FlightRepository flightRepository;

    @Override
    public FlightResponse createFlight(FlightRequest flightRequest) {
        LOG.debug(String.format("createFlight(%s)", flightRequest));
        Flight flight = FlightMapper.INSTANCE.flightRequestToFlight(flightRequest);
        flight.setCurrentAvailableSeat(flightRequest.getSeatAvailability());
        flight.setDuration(Duration.between(LocalDateTime.parse(flightRequest.getDepartureTime()),
                LocalDateTime.parse(flightRequest.getArrivalTime())));
        Flight savedFlight = flightRepository.save(flight);
        LOG.info(String.format("New flight with id : %d created", flight.getId()));
        return FlightMapper.INSTANCE.flightToFlightResponse(savedFlight);
    }

    @Override
    public List<FlightResponse> getAllFlights() {
        LOG.debug("getAllFlights()");
        return flightRepository.findAll().stream()
                .map(FlightMapper.INSTANCE::flightToFlightResponse)
                .toList();
    }

    @Override
    public FlightResponse getFlight(Long id) {
        LOG.debug(String.format("getFlight(%d)", id));
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FLIGHT, ID, id.toString()));
        LOG.info(String.format("Flight with id : %d retrieved : %s", id, flight));
        return FlightMapper.INSTANCE.flightToFlightResponse(flight);
    }

    @Override
    @Transactional
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) {
        LOG.debug(String.format("updateFlight(%s, %s)", id, flightRequest));
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FLIGHT, ID, id.toString()));
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
        LOG.info(String.format("Flight with id : %d gets updated", id));
        return FlightMapper.INSTANCE.flightToFlightResponse(flightRepository.save(flight));
    }

    @Override
    @Transactional
    public void deleteFlight(Long id) {
        LOG.debug(String.format("deleteFlight(%d)", id));
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FLIGHT, ID, id.toString()));
        flightRepository.delete(flight);
        LOG.info(String.format("Flight with id : %d deleted", id));
    }
}
