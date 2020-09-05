package com.goms.interfaces.initiation.server;

import com.goms.application.service.InitiationService;
import com.goms.domain.shared.DomainException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupController implements ApplicationListener<ApplicationReadyEvent> {

  private final Logger logger = LogManager.getLogger(StartupController.class);

  private final InitiationService initiationService;
  private final ApplicationContext applicationContext;

  @Autowired
  public StartupController(
      ApplicationContext applicationContext, InitiationService initiationService) {
    this.initiationService = initiationService;
    this.applicationContext = applicationContext;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    try {
      this.initiationService.storeDefaultData();
    } catch (DomainException ex) {
      this.logger.error("Error initiating the app", ex);
      int exitCode = SpringApplication.exit(this.applicationContext, () -> 0);
      System.exit(exitCode);
    }
  }
}
