package com.goms.interfaces.initiation.facade;

import com.goms.domain.shared.DomainException;

public interface InitiationServiceFacade {
    void storeDefaultData() throws DomainException;
}
