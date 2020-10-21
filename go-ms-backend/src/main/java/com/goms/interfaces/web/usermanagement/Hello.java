package com.goms.interfaces.web.usermanagement;

import com.goms.domain.model.privilege.Privilege;
import com.goms.domain.model.privilege.PrivilegeConstant;
import com.goms.domain.model.user.User;
import com.goms.infrastructure.utility.APIController;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.HashSet;

@RepositoryRestController
public class Hello {

  private final Logger logger = LogManager.getLogger(Hello.class);

  private final KafkaTemplate<?, ?> kafkaTemplate;
  private final KafkaAdmin kafkaAdmin;

  Hello(KafkaAdmin kafkaAdmin, KafkaTemplate<?, ?> kafkaTemplate) {
    this.kafkaAdmin = kafkaAdmin;
    this.kafkaTemplate = kafkaTemplate;
  }

  @PostMapping("/hello")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<String> helloAuth() {
    return new ResponseEntity<String>("Hi", HttpStatus.OK);
  }

  @PostMapping("/hello2")
  public ResponseEntity<String> helloNotAuth() {
    return new ResponseEntity<String>("Hi", HttpStatus.OK);
  }

  @PostMapping("/hello3")
  @PreAuthorize("hasAnyAuthority('OP_CREATE_USER')")
  public ResponseEntity<String> helloRole() {
    return new ResponseEntity<String>("Hi", HttpStatus.OK);
  }

  @PostMapping("/hello4")
  @PreAuthorize("hasAuthority('OP_DELETE_ACCOUNT')")
  public ResponseEntity<String> helloRoleNoThere() {
    return new ResponseEntity<String>("Hi", HttpStatus.OK);
  }

  @GetMapping("/list-topics")
  public void listTopics() {
    AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
    adminClient
        .listTopics()
        .names()
        .whenComplete((strings, throwable) -> strings.forEach(logger::info));
  }

  @GetMapping("/create-topic")
  public void createTopic(@RequestParam("topic-name") String topicName) {
    AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
    NewTopic topic = TopicBuilder.name(topicName).partitions(3).replicas(3).build();
    adminClient.createTopics(Collections.singleton(topic));
  }

  @GetMapping("/send-message-user")
  public void sendMessageUser(@RequestParam("topic-name") String topicName) {
    ((KafkaTemplate<Integer, Object>) this.kafkaTemplate)
        .executeInTransaction(
            operations ->
                operations.send(
                    topicName,
                    15,
                    new User(
                        "Ahmed", "pass", true, "first whatever", "last name", new HashSet<>())));
  }

  @GetMapping("/send-message-priv")
  public void sendMessagePrivilege(@RequestParam("topic-name") String topicName) {
    ((KafkaTemplate<Integer, Object>) this.kafkaTemplate)
        .executeInTransaction(
            operations ->
                operations.send(
                    topicName,
                    15,
                    new Privilege(PrivilegeConstant.OP_BRANCHES_MANAGEMENT)
                        .setDescription("Branch management")));
  }
}
