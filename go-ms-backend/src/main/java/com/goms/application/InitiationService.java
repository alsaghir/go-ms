package com.goms.application;

import com.goms.domain.shared.DomainException;

public interface InitiationService {

  void storeDefaultData() throws DomainException;
}
