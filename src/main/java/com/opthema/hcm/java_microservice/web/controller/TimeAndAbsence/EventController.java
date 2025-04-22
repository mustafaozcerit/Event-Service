package com.opthema.hcm.java_microservice.web.controller.TimeAndAbsence;

import com.opthema.hcm.java_microservice.application.service.TimeAndAbsence.EventService;
import com.opthema.hcm.java_microservice.domain.dto.TimeAndAbsence.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time_and_absence/event")
//@PreAuthorize("hasRole('ROLE_USER')")
public class EventController {
    @Autowired
    private EventService eventService;
    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> Events() {
      return eventService.getAllEvents();
    }
    @PostMapping("/create_event")
    public ResponseEntity<?> CreateEvent(@RequestBody EventDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }
    @DeleteMapping("/delete_event")
    public ResponseEntity<?> DeleteEvent(@RequestParam Long id) {
        return eventService.deleteEvent(id);
    }
    @PutMapping("/update_event")
    public ResponseEntity<?> DeleteEvent(@RequestBody EventDTO eventDTO) {
        return eventService.updateEvent(eventDTO);
    }


}
