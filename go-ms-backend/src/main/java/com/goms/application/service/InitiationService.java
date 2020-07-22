package com.goms.application.service;

import com.goms.domain.shared.DomainException;

public interface InitiationService {

  void storeDefaultData() throws DomainException;
}
