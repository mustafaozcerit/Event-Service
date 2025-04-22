//package com.opthema.hcm.java_microservice.web.controller;
//
//import com.opthema.hcm.java_microservice.application.service.MessageProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/api/rabbit")
//@RestController
//public class MessageController {
//
//    @Autowired
//    private MessageProducer messageProducer;
//
//    @PostMapping("/sendMessage")
//    public String sendMessage(@RequestParam String message) {
//        messageProducer.sendMessage(message);
//        return "Message sent to RabbitMQ: " + message;
//    }
//}
