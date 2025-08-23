package com.sazzad.event_ticket.services;

import com.sazzad.event_ticket.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {

    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
