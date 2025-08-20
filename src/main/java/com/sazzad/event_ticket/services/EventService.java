package com.sazzad.event_ticket.services;

import com.sazzad.event_ticket.domain.CreateEventRequest;
import com.sazzad.event_ticket.domain.entities.Event;

import java.util.UUID;

public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequest event);
}
