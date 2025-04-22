package com.opthema.hcm.java_microservice.web.controller;

import com.opthema.hcm.java_microservice.application.service.JwtService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {
    @Autowired
    private JwtService jwtService;

//    @KafkaListener(topics = "my-topic", groupId = "my-group5")
    public void listen(ConsumerRecord<String, String> record) {
        String jwtToken = new String(record.headers().lastHeader("Authorization").value());

        if (jwtService.validateToken(jwtToken)) {
            System.out.println("Consumed message: " + record.value());
        } else {
            System.out.println("Invalid token. Message ignored.");
        }
    }

}