package com.goms.domain.shared;

import java.util.HashSet;
import java.util.Set;

public enum DomainError {
  UNEXPECTED_ERROR(1, "Unexpected error. Please contact administrator."),
  EXPECTED_ERROR(2, "Expected error !"),
  BAD_CREDENTIALS(3, "Bad credentials Entered !"),
  ;

  static {
    Set<Integer> checkedErrorCode = new HashSet<>();
    for (DomainError error : DomainError.values()) {
      if (checkedErrorCode.contains(error.getErrorCode()))
        throw new IllegalStateException(
            "Duplicated Error Code Id" + error,
            new DomainException("error code should not be repeated", DomainError.UNEXPECTED_ERROR));
      checkedErrorCode.add(error.getErrorCode());
    }
  }

  private final int errorCode;
  private final String errorMessage;

  DomainError(int errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  @Override
  public String toString() {
    return "errorCode=" + errorCode + ", errorMessage='" + errorMessage + '\'';
  }
}
