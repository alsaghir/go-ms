package com.goms.interfaces.messaging;

import com.goms.domain.model.user.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.stereotype.Component;

@KafkaListener(
    id = "UserListener",
    topics = "user-topic",
    groupId = "UserListener",
    clientIdPrefix = "cl")
@Component
@Lazy(value = false)
public class UserListener {

  @KafkaHandler
  public void listen(
          User user, ConsumerRecordMetadata meta, ConsumerRecord<?, ?> record) {
    System.out.println("Normal test Listener");
    System.out.println(user);
    System.out.println(meta);
    System.out.println(record);
  }

  @KafkaHandler(isDefault = true)
  public void listenDefault(
      Object object, ConsumerRecordMetadata meta, ConsumerRecord<?, ?> record) {
    System.out.println("Default Listener");
    System.out.println(object);
    System.out.println(meta);
    System.out.println(record);
  }
}
