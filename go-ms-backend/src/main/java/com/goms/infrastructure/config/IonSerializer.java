package com.goms.infrastructure.config;

import com.fasterxml.jackson.dataformat.ion.IonObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class IonSerializer<T> extends JsonSerializer<T> implements Serializer<T> {

  public IonSerializer(IonObjectMapper ionObjectMapper) {
    super(ionObjectMapper);
  }
}
