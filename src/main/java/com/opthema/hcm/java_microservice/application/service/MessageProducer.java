//package com.opthema.hcm.java_microservice.application.service;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageProducer {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private Queue exampleQueue;
//
//    // Mesajı RabbitMQ kuyruğuna gönderme
//    public void sendMessage(String message) {
//        rabbitTemplate.convertAndSend(exampleQueue.getName(), message);
//    }
//}
