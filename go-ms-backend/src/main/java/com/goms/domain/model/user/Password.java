package com.goms.domain.model.user;

import com.goms.domain.service.CryptoService;
import com.goms.domain.shared.DomainError;
import com.goms.domain.shared.DomainException;
import com.goms.domain.shared.ValueObject;

public class Password implements ValueObject<Password> {

  private final PasswordState state;
  private final String value;

  public Password(String value, PasswordState state) {
    this.value = value;
    this.state = state;
  }

  public PasswordState state() {
    return state;
  }

  public String value() {
    return value;
  }

  @Override
  public boolean sameValueAs(Password other) {
    if (other == null) return false;
    else return state.equals(other.state) && value.equals(other.value);
  }

  public Password checkStateAndGetRaw(CryptoService cryptoService, String rsaPrivateKey)
          throws DomainException {
    if (this.state.equals(PasswordState.BASE64_ENCRYPTED))
      return new Password(cryptoService.decryptedPassword(value, rsaPrivateKey), PasswordState.RAW);
    else if (this.state.equals(PasswordState.RAW))
      return this;
    else throw new DomainException("Unknown password state", DomainError.UNEXPECTED_ERROR);
  }
}
