package com.sazzad.event_ticket.repositories;

import com.sazzad.event_ticket.domain.entities.QrCode;
import com.sazzad.event_ticket.domain.entities.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID>{

}
