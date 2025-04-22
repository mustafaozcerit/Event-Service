package com.opthema.hcm.java_microservice.infrastructure.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Value("${kafka.topic.my-topic}")
    private String TOPIC;

    // Kafka Producer Factory Bean
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // Kafka Consumer Factory Bean
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Bu satır eklenmeli
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    // Kafka Consumer Bean
    @Bean
    public MessageListenerContainer messageListenerContainer(ConsumerFactory<String, String> consumerFactory) {
        // Kafka Message Listener
        MessageListener<String, String> messageListener = record -> {
            System.out.println("Consumed message: " + record.value());
            System.out.println("Message Key: " + record.key());  // Mesajın key bilgisi
            System.out.println("Message Partition: " + record.partition());  // Mesajın hangi partition'dan geldiği
            System.out.println("Message Offset: " + record.offset());  // Mesajın offset bilgisi;
        };

        // ContainerProperties kullanımı
        ContainerProperties containerProps = new ContainerProperties(TOPIC);
        containerProps.setMessageListener(messageListener);

        // ConcurrentMessageListenerContainer ile container oluşturuluyor
        return new ConcurrentMessageListenerContainer<>(consumerFactory, containerProps);
    }

    // KafkaTemplate Bean (Producer için)
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
