package com.goms.interfaces.messaging;

import org.springframework.stereotype.Component;

/*@KafkaListener(
    id = "UserListener",
    topics = "user-topic",
    groupId = "UserListener",
    clientIdPrefix = "cl",
    autoStartup = "false")*/
@Component
//@Lazy(value = false)
public class UserListener {

 /* @KafkaHandler
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
  }*/
}
