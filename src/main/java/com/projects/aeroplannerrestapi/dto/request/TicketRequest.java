package com.projects.aeroplannerrestapi.dto.request;

import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {
    private Long id;
    @NotNull
    private Long reservationId;
}
