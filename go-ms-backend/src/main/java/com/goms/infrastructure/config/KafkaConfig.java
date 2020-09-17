package com.goms.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;

//@Configuration
public class KafkaConfig {

  @Autowired
  KafkaConfig(KafkaAdmin kafkaAdmin) {

    // additional
    kafkaAdmin.setFatalIfBrokerNotAvailable(true);
  }

  @Bean("ionObjectMapper")
  IonObjectMapper ionObjectMapper() {
    return new IonObjectMapper();
  }

  /** Configuring the default object mapper provided by Spring due providing other mappers like
   * {@link IonObjectMapper} in same project like {@link #ionObjectMapper()} for example
   *
   * @param builder provided by Spring boot
   * @return {@link ObjectMapper} that is provided by spring
   */
  @Bean("springObjectMapper")
  @Autowired
  @Primary
  ObjectMapper createSpringObjectMapper(Jackson2ObjectMapperBuilder builder) {
    return builder.createXmlMapper(false).build();
  }

  @Bean
  public ProducerFactory<Integer, Object> kafkaProducerFactory(
      KafkaProperties properties, DefaultKafkaProducerFactoryCustomizer customizers) {

    DefaultKafkaProducerFactory<Integer, Object> factory =
        new DefaultKafkaProducerFactory<>(properties.buildProducerProperties());

    factory.setKeySerializer(new IntegerSerializer());
    factory.setValueSerializer(new IonSerializer<>(ionObjectMapper()));
    factory.setProducerPerThread(true);

    String transactionIdPrefix = properties.getProducer().getTransactionIdPrefix();
    if (transactionIdPrefix != null) {
      factory.setTransactionIdPrefix(transactionIdPrefix);
    }

    customizers.customize(factory);

    return factory;
  }

  @Bean
  public NewTopic topic1() {
    return TopicBuilder.name("user-topic")
            .partitions(3)
            .replicas(1)
            .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "snappy")
            .build();
  }

  @Bean
  public KafkaTemplate<?, ?> kafkaTemplate(
      ProducerFactory<Integer, Object> kafkaProducerFactory,
      ProducerListener<Integer, Object> kafkaProducerListener,
      KafkaProperties properties) {

    KafkaTemplate<Integer, Object> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
    kafkaTemplate.setMessageConverter(new IonMessageConverter(ionObjectMapper()));
    kafkaTemplate.setProducerListener(kafkaProducerListener);
    kafkaTemplate.setDefaultTopic(properties.getTemplate().getDefaultTopic());
    return kafkaTemplate;
  }

  @Bean
  public ProducerListener<Integer, Object> kafkaProducerListener() {
    return new LoggingProducerListener<>();
  }

  @Bean
  public ConsumerFactory<?, ?> kafkaConsumerFactory(
      KafkaProperties properties, DefaultKafkaConsumerFactoryCustomizer customizers) {

    DefaultKafkaConsumerFactory<Integer, Object> factory =
        new DefaultKafkaConsumerFactory<>(properties.buildConsumerProperties());
    customizers.customize(factory);

    IonDeserializer<Object> d = new IonDeserializer<>(ionObjectMapper());
    d.addTrustedPackages("*");
    factory.setKeyDeserializer(new IntegerDeserializer());
    factory.setValueDeserializer(d);

    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<?, ?> c(
      ConcurrentKafkaListenerContainerFactory<?, ?> factory) {
    factory.getContainerProperties().setEosMode(ContainerProperties.EOSMode.BETA);
    factory.getContainerProperties().setLogContainerConfig(true);
    factory.setAutoStartup(true);
    return factory;
  }
}
