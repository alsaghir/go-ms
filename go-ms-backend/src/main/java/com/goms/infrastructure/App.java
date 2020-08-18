package com.goms.infrastructure;

import com.goms.interfaces.messaging.UserListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    basePackageClasses = {App.class,
            com.goms.application.SpringScanClass.class,
            com.goms.interfaces.SpringScanClass.class,
            UserListener.class},
    excludeFilters = {
      @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
      @ComponentScan.Filter(
          type = FilterType.CUSTOM,
          classes = AutoConfigurationExcludeFilter.class)
    })
@EnableKafka
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
