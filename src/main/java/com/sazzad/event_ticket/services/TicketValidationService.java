package com.sazzad.event_ticket.services;

import com.sazzad.event_ticket.domain.entities.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {

    TicketValidation validateTicketByQrCode(UUID qrCodeId);

    TicketValidation validateTicketManually(UUID ticketId);
}
