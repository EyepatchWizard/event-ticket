package com.sazzad.event_ticket.mapers;

import com.sazzad.event_ticket.domain.CreateEventRequest;
import com.sazzad.event_ticket.domain.CreateTicketTypesRequest;
import com.sazzad.event_ticket.domain.dtos.CreateEventRequestDto;
import com.sazzad.event_ticket.domain.dtos.CreateEventResponseDto;
import com.sazzad.event_ticket.domain.dtos.CreateTicketTypesRequestDto;
import com.sazzad.event_ticket.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypesRequest fromDto(CreateTicketTypesRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);
}
