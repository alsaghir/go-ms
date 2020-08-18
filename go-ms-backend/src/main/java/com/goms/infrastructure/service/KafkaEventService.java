package com.goms.infrastructure.service;

import com.goms.domain.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventService implements EventService {

    private final KafkaTemplate<?,?> kafkaTemplate;

    @Autowired
    KafkaEventService(KafkaTemplate<?, ?> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public <T> void publish(Integer key, T message) {
        ((KafkaTemplate<Integer, T>) this.kafkaTemplate)
                .executeInTransaction(operations -> operations.send("user-topic", key, message));
    }
}
