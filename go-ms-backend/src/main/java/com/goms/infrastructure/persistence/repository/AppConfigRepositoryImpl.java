package com.goms.infrastructure.persistence.repository;


import com.goms.domain.model.config.AppConfig;
import com.goms.domain.model.config.AppConfigRepository;
import com.goms.infrastructure.config.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AppConfigRepositoryImpl implements AppConfigRepository {

  private final ConfigProperties configProperties;

  @Autowired
  public AppConfigRepositoryImpl(ConfigProperties configProperties) {
    this.configProperties = configProperties;
  }

  @Override
  public AppConfig findAll() {
    return AppConfig.read(
        this.configProperties.isInitializingDataRequired(),
        this.configProperties.getRsaPrivateKey(),
        this.configProperties.getJwtSigningKey(),
        this.configProperties.getJwtExpirationTime());
  }
}
