package com.sazzad.event_ticket.services.impl;

import com.sazzad.event_ticket.domain.entities.QrCode;
import com.sazzad.event_ticket.domain.entities.Ticket;
import com.sazzad.event_ticket.domain.entities.TicketValidation;
import com.sazzad.event_ticket.domain.enums.QrCodeStatusEnum;
import com.sazzad.event_ticket.domain.enums.TicketValidationStatusEnum;
import com.sazzad.event_ticket.domain.enums.TicketValidationMethodEnum;
import com.sazzad.event_ticket.exceptions.QrCodeNotFoundException;
import com.sazzad.event_ticket.exceptions.TicketTypeNotFoundException;
import com.sazzad.event_ticket.repositories.QrCodeRepository;
import com.sazzad.event_ticket.repositories.TicketRepository;
import com.sazzad.event_ticket.repositories.TicketValidationRepository;
import com.sazzad.event_ticket.services.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(
                        String.format(
                                "QR Code with ID %s was not found", qrCodeId
                        )
                ));

        Ticket ticket = qrCode.getTicket();

        return validateTicket(ticket, TicketValidationMethodEnum.QR_SCAN);

    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethodEnum ticketValidationMethodEnum) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(ticketValidationMethodEnum);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketTypeNotFoundException::new);

        return validateTicket(ticket, TicketValidationMethodEnum.MANUAL);
    }
}
