package com.goms.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class RepositoryRestConfig implements RepositoryRestConfigurer {

  private final Jackson2ObjectMapperBuilder objectMapperBuilder;
  private final RepositoryRestProperties properties;

  @Autowired
  RepositoryRestConfig(
      Jackson2ObjectMapperBuilder objectMapperBuilder, RepositoryRestProperties properties) {
    this.objectMapperBuilder = objectMapperBuilder;
    this.properties = properties;
  }

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.setRepositoryDetectionStrategy(
        RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
    config.exposeIdsFor();

    this.properties.applyTo(config);
  }

  @Override
  public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
    if (this.objectMapperBuilder != null) {
      this.objectMapperBuilder.configure(objectMapper);
    }
  }
}
