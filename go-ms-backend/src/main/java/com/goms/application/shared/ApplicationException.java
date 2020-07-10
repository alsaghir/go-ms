package com.goms.application.shared;

import com.goms.domain.shared.DomainError;
import com.goms.domain.shared.DomainException;

import java.util.HashSet;
import java.util.Set;

public class ApplicationException extends Exception {

  private final Set<DomainError> domainErrors;

  public ApplicationException(Exception exception) {
    super(exception);
    if (exception instanceof DomainException)
      this.domainErrors = ((DomainException) exception).domainErrors();
    else {
      this.domainErrors = new HashSet<>();
      this.domainErrors.add(DomainError.UNEXPECTED_ERROR);
    }
  }

  public boolean hasErrorCodes() {
    return (this.domainErrors.size() > 0);
  }

  public Set<DomainError> domainErrors() {
    return this.domainErrors;
  }
}
