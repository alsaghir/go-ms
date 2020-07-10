package com.goms.domain.shared;

import java.util.Set;

public class DomainException extends Exception {

  private final Set<DomainError> domainErrors;

  public DomainException(String debugMessage, DomainError... errorCodes) {
    super(debugMessage);
    this.domainErrors = Set.of(errorCodes);
  }

  public DomainException(String debugMessage, Throwable ex, DomainError... domainErrors) {
    super(debugMessage, ex);
    this.domainErrors = Set.of(domainErrors);
  }

  public Set<DomainError> domainErrors() {
    return this.domainErrors;
  }

  @Override
  public String toString() {
    return "DomainException {errorCodes= " + domainErrors + '}';
  }
}
