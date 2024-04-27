package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private String passengerId;
    private String flightId;
    private int seatNumber;
    private String issueDate;
    private TicketStatusEnum ticketStatusEnum;
}
