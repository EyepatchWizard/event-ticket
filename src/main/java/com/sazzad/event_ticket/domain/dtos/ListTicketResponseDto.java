package com.sazzad.event_ticket.domain.dtos;

import com.sazzad.event_ticket.domain.enums.TicktetStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseDto {

    private UUID id;
    private TicktetStatusEnum status;
    private ListTicketTicketTypeResponseDto ticketType;
}
