package com.projects.aeroplannerrestapi.mapper;

import com.projects.aeroplannerrestapi.dto.response.TicketResponse;
import com.projects.aeroplannerrestapi.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    TicketResponse ticketToTicketResponse(Ticket ticket);
}
