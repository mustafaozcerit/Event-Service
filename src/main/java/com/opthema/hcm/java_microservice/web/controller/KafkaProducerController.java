package com.opthema.hcm.java_microservice.web.controller;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.my-topic}")
    private String TOPIC;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String sendMessage(@RequestParam("message") String message,
                              @RequestHeader("Authorization") String authorization) {
        String jwtToken = authorization.replace("Bearer ", "");  // "Bearer " kısmını kaldırıyoruz
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "key", message);
        record.headers().add("Authorization", jwtToken.getBytes());
        kafkaTemplate.send(record);

        return "Message sent to Kafka topic " + TOPIC + " with JWT token: " + jwtToken;
    }
}
