package com.goms.domain.model.config;

import com.goms.domain.shared.Entity;

public class AppConfig implements Entity<AppConfig> {

  private static AppConfig appConfig = null;
  private boolean initializingDataRequired;
  private String jwtSigningKey;
  private String rsaPrivateKey;
  private String jwtExpirationTime;

  private AppConfig() {}

  public static AppConfig read(
      boolean isInitializingDataRequired,
      String rsaPrivateKey,
      String jwtSigningKey,
      String jwtExpirationTime) {

    if (appConfig == null) {
      synchronized (AppConfig.class) {
        if (appConfig == null) {

          appConfig =
                  new AppConfig()
                          .initializingDataRequired(isInitializingDataRequired)
                          .rsaPrivateKey(rsaPrivateKey)
                          .jwtSigningKey(jwtSigningKey)
                          .jwtExpirationTime(jwtExpirationTime);
          ;
        }
      }
    }

    return appConfig;
  }

  private AppConfig jwtSigningKey(String jwtSigningKey) {
    this.jwtSigningKey = jwtSigningKey;
    return this;
  }

  private AppConfig jwtExpirationTime(String jwtExpirationTime) {
    this.jwtExpirationTime = jwtExpirationTime;
    return this;
  }

  public String jwtExpirationTime() {
    return jwtExpirationTime;
  }

  public String jwtSigningKey() {
    return jwtSigningKey;
  }

  public String rsaPrivateKey() {
    return rsaPrivateKey;
  }

  public AppConfig rsaPrivateKey(String rsaPrivateKey) {
    this.rsaPrivateKey = rsaPrivateKey;
    return this;
  }

  private AppConfig initializingDataRequired(boolean initializingDataRequired) {
    this.initializingDataRequired = initializingDataRequired;
    return this;
  }

  public boolean initializingDataRequired() {
    return this.initializingDataRequired;
  }


  @Override
  public boolean sameIdentityAs(AppConfig other) {
    return true;
  }
}
