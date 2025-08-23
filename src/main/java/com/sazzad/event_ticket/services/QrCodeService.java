package com.sazzad.event_ticket.services;

import com.sazzad.event_ticket.domain.entities.QrCode;
import com.sazzad.event_ticket.domain.entities.Ticket;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);
}
