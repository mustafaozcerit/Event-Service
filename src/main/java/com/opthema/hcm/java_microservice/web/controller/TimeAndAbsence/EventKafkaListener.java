package com.opthema.hcm.java_microservice.web.controller.TimeAndAbsence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opthema.hcm.java_microservice.application.service.JwtService;
import com.opthema.hcm.java_microservice.application.service.TimeAndAbsence.EventService;
import com.opthema.hcm.java_microservice.domain.dto.TimeAndAbsence.EventDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class EventKafkaListener {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "my-topic", groupId = "my-event")
    public void listen(ConsumerRecord<String, String> record) throws JsonProcessingException {
        String jwtToken = new String(record.headers().lastHeader("Authorization").value());

        String JsonEvent = record.value();
        EventDTO eventDTO = objectMapper.readValue(JsonEvent, EventDTO.class);

        if (jwtService.validateToken(jwtToken)) {
            eventService.createEvent(eventDTO);
            System.out.println("Consumed message: " + record.value());
        } else {
            System.out.println("Invalid token. Message ignored.");
        }
    }
}
