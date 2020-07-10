package com.goms.infrastructure.config;

import com.goms.infrastructure.utility.AESUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class DataSourceConfig {

  /**
   * Configuration for real data source of {@link HikariDataSource} HikariCP pooled data source.
   *
   * @param dataSourceProperties is data source config been read from application.yaml config
   * @return {@link HikariDataSource} HikariCP pooled data source
   * @throws Exception mostly related to password encryption
   */
  @Bean
  public HikariDataSource dataSource(DataSourceProperties dataSourceProperties) throws Exception {

    return dataSourceProperties
        .initializeDataSourceBuilder()
        .password(
            new String(
                AESUtil.decrypt(
                    Base64.getDecoder().decode(dataSourceProperties.getPassword().getBytes()),
                    new SecretKeySpec("123456789ABCDEFG".getBytes(), "AES"))))
        .type(HikariDataSource.class)
        .build();
  }
}
