package com.projects.aeroplannerrestapi.scheduler;

import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.entity.Ticket;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.*;


@Component
@RequiredArgsConstructor
public class ReservationCheckTask {

    private static final Log LOG = LogFactory.getLog(ReservationCheckTask.class);

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void checkReservations() {
        LOG.debug("checkReservations()");
        List<Reservation> reservations = reservationRepository.findByReservationStatus(ReservationStatusEnum.CONFIRMED);
        reservations.forEach(reservation -> {
            String reservationId = reservation.getId().toString();
            LOG.info(String.format("Reservation with id %s is being checked", reservationId));
            Ticket ticket = ticketRepository.findByReservationId(reservationId)
                    .orElseThrow(() -> new ResourceNotFoundException(TICKET, RESERVATION_ID, reservationId));
            if (!ticket.getTicketStatusEnum().toString().equals(TicketStatusEnum.ISSUED.name())) {
                LOG.warn("\tTicket is not issued");
                String reservationDate = reservation.getReservationDate();
                LocalDateTime givenDateTime = LocalDateTime.parse(reservationDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                LocalDateTime twoDaysBefore = givenDateTime.minusDays(2);
                LocalDateTime currentDate = LocalDateTime.now();
                if (currentDate.isAfter(twoDaysBefore) && currentDate.isBefore(givenDateTime)) {
                    Long flightId = reservation.getFlightId();
                    reservation.setReservationStatus(ReservationStatusEnum.CANCELLED);
                    Reservation updatedReservation = reservationRepository.save(reservation);
                    LOG.warn("\tReservation got cancelled due to the fact that less than two days left until flight");
                    Flight flight = flightRepository.findById(flightId)
                            .orElseThrow(() -> new ResourceNotFoundException(FLIGHT, ID, flightId.toString()));
                    flight.setCurrentAvailableSeat(flight.getCurrentAvailableSeat() + updatedReservation.getSeatNumber());
                    flightRepository.save(flight);
                }
            }
        });
    }
}
