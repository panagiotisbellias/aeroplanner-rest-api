package com.projects.aeroplannerrestapi.mapper;

import com.projects.aeroplannerrestapi.dto.ReservationDto;
import com.projects.aeroplannerrestapi.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    Reservation reservationDtoToReservation(ReservationDto reservationDto);

    ReservationDto reservationToReservationDto(Reservation reservation);
}
