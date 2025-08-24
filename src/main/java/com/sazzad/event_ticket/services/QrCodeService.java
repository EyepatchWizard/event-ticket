package com.sazzad.event_ticket.services;

import com.sazzad.event_ticket.domain.entities.QrCode;
import com.sazzad.event_ticket.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
