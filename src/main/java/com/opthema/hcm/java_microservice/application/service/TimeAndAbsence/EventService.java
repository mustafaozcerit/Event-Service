package com.opthema.hcm.java_microservice.application.service.TimeAndAbsence;

import com.opthema.hcm.java_microservice.domain.dto.TimeAndAbsence.EventDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {
    ResponseEntity<List<EventDTO>> getAllEvents();
    ResponseEntity<?> createEvent(EventDTO eventDTO);
    ResponseEntity<?> deleteEvent(Long eventId);
    ResponseEntity<?> updateEvent(EventDTO eventDTO);
}
