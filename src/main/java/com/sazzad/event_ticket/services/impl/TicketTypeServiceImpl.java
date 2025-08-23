package com.sazzad.event_ticket.services.impl;

import com.sazzad.event_ticket.domain.entities.Ticket;
import com.sazzad.event_ticket.domain.entities.TicketType;
import com.sazzad.event_ticket.domain.entities.User;
import com.sazzad.event_ticket.domain.enums.TicktetStatusEnum;
import com.sazzad.event_ticket.exceptions.TicketTypeNotFoundException;
import com.sazzad.event_ticket.exceptions.TicketsSoldOutException;
import com.sazzad.event_ticket.repositories.TicketRepository;
import com.sazzad.event_ticket.repositories.TicketTypeRepository;
import com.sazzad.event_ticket.repositories.UserRepository;
import com.sazzad.event_ticket.services.QrCodeService;
import com.sazzad.event_ticket.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User with ID '%s' was not found", userId)
        ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(() -> new TicketTypeNotFoundException(
                String.format("Ticket type with ID '%s' was not found", ticketTypeId)
        ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if (purchasedTickets + 1 > totalAvailable) {
            throw new TicketsSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicktetStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}
