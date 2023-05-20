package com.kodlamaio.maintanceservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(MaintenanceCreatedEvent event) {
        log.info(String.format("maintenance-created event => %s", event.toString()));
        Message<MaintenanceCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "maintenance-created")
                .build();

        kafkaTemplate.send(message);
    }
}