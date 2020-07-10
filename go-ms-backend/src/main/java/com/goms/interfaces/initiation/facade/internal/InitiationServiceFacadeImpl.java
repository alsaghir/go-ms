package com.goms.interfaces.initiation.facade.internal;


import com.goms.application.service.InitiationService;
import com.goms.interfaces.initiation.facade.InitiationServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitiationServiceFacadeImpl implements InitiationServiceFacade {

  private final InitiationService initiationService;

  @Autowired
  public InitiationServiceFacadeImpl(InitiationService initiationService) {
    this.initiationService = initiationService;
  }

  @Override
  public void storeDefaultData() {
    this.initiationService.storeDefaultData();
  }
}
