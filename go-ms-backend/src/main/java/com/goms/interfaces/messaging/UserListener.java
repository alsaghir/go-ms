package com.goms.interfaces.messaging;

import com.goms.application.service.data.UserDetailsData;
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
    clientIdPrefix = "cl",
    autoStartup = "true")
@Component
@Lazy(value = false)
public class UserListener {

  @KafkaHandler
  public void listen(
      UserDetailsData userDetailsData, ConsumerRecordMetadata meta, ConsumerRecord<?, ?> record) {
    System.out.println("Normal test Listener");
    System.out.println(userDetailsData);
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
