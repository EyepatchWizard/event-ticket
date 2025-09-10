package com.sazzad.event_ticket.controllers;

import com.sazzad.event_ticket.domain.dtos.GetEventDetailsResponseDto;
import com.sazzad.event_ticket.domain.dtos.GetPublishedEventDetailsResponseDto;
import com.sazzad.event_ticket.domain.dtos.ListPublishedEventResponseDto;
import com.sazzad.event_ticket.domain.entities.Event;
import com.sazzad.event_ticket.mapers.EventMapper;
import com.sazzad.event_ticket.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents (
            @RequestParam(required = false) String q,
            Pageable pageable) {

        Page<Event> events;
        if (null != q && !q.trim().isEmpty()) {
            events = eventService.searchPublishedEvents(q, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }

       return ResponseEntity.ok(
               events.map(eventMapper::toListPublishedEventResponseDto)
       );
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEventDetails (
            @PathVariable UUID eventId
            ) {
        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

