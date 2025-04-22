package com.opthema.hcm.java_microservice.application.service.TimeAndAbsence;

import com.opthema.hcm.java_microservice.domain.dto.TimeAndAbsence.EventDTO;
import com.opthema.hcm.java_microservice.domain.model.Events;
import com.opthema.hcm.java_microservice.infrastructure.repository.EventsRepository;
import com.opthema.hcm.java_microservice.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements  EventService{
    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<Events> allEvents = eventsRepository.findAll();

        List<EventDTO> dtoList = allEvents.stream()
                .map(ev -> eventMapper.eventToEventDTO(ev))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> createEvent(EventDTO eventDTO){
        Events event = eventMapper.eventDTOToEvent(eventDTO);
        eventsRepository.save(event);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<?> deleteEvent(Long eventId){
        eventsRepository.deleteAllById(Collections.singleton(eventId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> updateEvent(EventDTO eventDTO) {

        Events event = eventMapper.eventDTOToEvent(eventDTO);
        event.setId(eventDTO.getId());

        Optional<Events> existingEvent = eventsRepository.findById(event.getId());

        if (existingEvent.isPresent()) {
            Events eventToUpdate = existingEvent.get();

            eventToUpdate.setName(event.getName());
            eventToUpdate.setDate(event.getDate());
            eventToUpdate.setUserOrGroupType(event.getUserOrGroupType());
            eventToUpdate.setUserOrGroupsFilterValue(event.getUserOrGroupsFilterValue());
            eventToUpdate.setAppUserIds(event.getAppUserIds());

            eventsRepository.save(eventToUpdate);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
