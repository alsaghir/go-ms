package com.goms.interfaces.initiation.server;

import com.goms.domain.shared.DomainException;
import com.goms.interfaces.initiation.facade.InitiationServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupController implements ApplicationListener<ApplicationReadyEvent> {

  private final Logger logger = LoggerFactory.getLogger(StartupController.class);

  private final InitiationServiceFacade initiationServiceFacade;
  private final ApplicationContext applicationContext;

  @Autowired
  public StartupController(
      ApplicationContext applicationContext, InitiationServiceFacade initiationServiceFacade) {
    this.initiationServiceFacade = initiationServiceFacade;
    this.applicationContext = applicationContext;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    try {
      this.initiationServiceFacade.storeDefaultData();
    } catch (DomainException ex) {
      this.logger.error("Error initiating the app", ex);
      int exitCode = SpringApplication.exit(this.applicationContext, () -> 0);
      System.exit(exitCode);
    }
  }
}
