package com.projects.aeroplannerrestapi.mapper;

import com.projects.aeroplannerrestapi.dto.FlightDto;
import com.projects.aeroplannerrestapi.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlightMapper {

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    FlightDto flightToFlightDto(Flight flight);

    Flight flightDtoToFlight(FlightDto flightDto);
}
