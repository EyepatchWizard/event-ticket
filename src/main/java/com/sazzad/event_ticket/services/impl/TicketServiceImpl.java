package com.sazzad.event_ticket.services.impl;

import com.sazzad.event_ticket.domain.entities.Ticket;
import com.sazzad.event_ticket.repositories.TicketRepository;
import com.sazzad.event_ticket.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository  ticketRepository;

    @Override
    public Page<Ticket> listTicketForUser(UUID userId, Pageable pageable) {

        return ticketRepository.findByPurchaserId(userId, pageable);
    }

    @Override
    public Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId) {
        return ticketRepository.findByIdAndPurchaserId(ticketId, userId);
    }
}
