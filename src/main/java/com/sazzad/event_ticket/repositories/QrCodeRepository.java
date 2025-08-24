package com.sazzad.event_ticket.repositories;

import com.sazzad.event_ticket.domain.entities.QrCode;
import com.sazzad.event_ticket.domain.enums.QrCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {

    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID ticketPurchaserId);

    Optional<QrCode> findByIdAndStatus(UUID id, QrCodeStatusEnum status);
}
