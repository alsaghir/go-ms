package com.goms.infrastructure.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class IonDeserializer<T> extends JsonDeserializer<T> implements Deserializer<T> {

  public IonDeserializer(IonObjectMapper ionObjectMapper) {
    super(ionObjectMapper);
  }

  public IonDeserializer(Class<? super T> targetType, IonObjectMapper ionObjectMapper) {
    super(targetType, ionObjectMapper);
  }

  public IonDeserializer(TypeReference<? super T> targetType, IonObjectMapper ionObjectMapper) {
    super(targetType, ionObjectMapper);
  }

  public IonDeserializer(
      Class<? super T> targetType, IonObjectMapper ionObjectMapper, boolean useHeadersIfPresent) {
    super(targetType, ionObjectMapper, useHeadersIfPresent);
  }

  public IonDeserializer(
      TypeReference<? super T> targetType,
      IonObjectMapper ionObjectMapper,
      boolean useHeadersIfPresent) {
    super(targetType, ionObjectMapper, useHeadersIfPresent);
  }

  public IonDeserializer(
      JavaType targetType, IonObjectMapper ionObjectMapper, boolean useHeadersIfPresent) {
    super(targetType, ionObjectMapper, useHeadersIfPresent);
  }
}
