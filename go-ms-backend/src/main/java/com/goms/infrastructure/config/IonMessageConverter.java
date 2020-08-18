package com.goms.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper;
import org.springframework.kafka.support.converter.BytesJsonMessageConverter;

public class IonMessageConverter extends BytesJsonMessageConverter {
  private final IonObjectMapper ionObjectMapper;

  public IonMessageConverter(IonObjectMapper ionObjectMapper) {
    super(ionObjectMapper);
    this.ionObjectMapper = ionObjectMapper;
  }

  /**
   * Return the object mapper.
   *
   * @return the mapper.
   */
  @Override
  protected ObjectMapper getObjectMapper() {
    return this.ionObjectMapper;
  }
}
