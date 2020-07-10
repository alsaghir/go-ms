package com.goms.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.config")
public class ConfigProperties {

  private String jwtSigningKey;
  private String jwtExpirationTime;
  private boolean initializingDataRequired;
  private String rsaPrivateKey;

  public String getJwtExpirationTime() {
    return jwtExpirationTime;
  }

  public ConfigProperties setJwtExpirationTime(String jwtExpirationTime) {
    this.jwtExpirationTime = jwtExpirationTime;
    return this;
  }

  public String getJwtSigningKey() {
    return jwtSigningKey;
  }

  public void setJwtSigningKey(String jwtSigningKey) {
    this.jwtSigningKey = jwtSigningKey;
  }

  public boolean isInitializingDataRequired() {
    return initializingDataRequired;
  }

  public void setInitializingDataRequired(boolean initializingDataRequired) {
    this.initializingDataRequired = initializingDataRequired;
  }

  public String getRsaPrivateKey() {
    return rsaPrivateKey;
  }

  public ConfigProperties setRsaPrivateKey(String rsaPrivateKey) {
    this.rsaPrivateKey = rsaPrivateKey;
    return this;
  }
}
