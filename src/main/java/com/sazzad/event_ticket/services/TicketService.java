package com.sazzad.event_ticket.services;

import com.sazzad.event_ticket.domain.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TicketService {

    Page<Ticket> listTicketForUser(UUID userId, Pageable pageable);

    Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId);
}
