package com.sazzad.event_ticket.controllers;

import com.sazzad.event_ticket.domain.dtos.TicketValidationRequestDto;
import com.sazzad.event_ticket.domain.dtos.TicketValidationResponseDto;
import com.sazzad.event_ticket.domain.entities.TicketValidation;
import com.sazzad.event_ticket.domain.enums.TicketValidationMethodEnum;
import com.sazzad.event_ticket.mapers.TicketValidationMapper;
import com.sazzad.event_ticket.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/ticket-validation")
@RequiredArgsConstructor
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket(
            @RequestBody TicketValidationRequestDto ticketValidationRequestDto
    ) {

        TicketValidationMethodEnum method = ticketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;

        if(method.equals(TicketValidationMethodEnum.QR_SCAN)) {
             ticketValidation = ticketValidationService.validateTicketByQrCode(
                    ticketValidationRequestDto.getId());
        } else {
             ticketValidation = ticketValidationService.validateTicketManually(
                    ticketValidationRequestDto.getId());
        }

        return ResponseEntity.ok(
                ticketValidationMapper.toTicketValidationResponseDto(ticketValidation)
        );
    }
}
