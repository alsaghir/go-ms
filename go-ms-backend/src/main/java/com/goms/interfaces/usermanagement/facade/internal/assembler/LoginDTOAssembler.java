package com.goms.interfaces.usermanagement.facade.internal.assembler;

import com.goms.domain.model.user.Password;
import com.goms.domain.model.user.PasswordState;
import com.goms.domain.model.user.User;
import com.goms.interfaces.usermanagement.facade.dto.LoginRequest;
import com.goms.interfaces.usermanagement.facade.dto.LoginResponse;

public class LoginDTOAssembler {
  public User fromDTO(LoginRequest loginRequest) {
    return new User(
        loginRequest.getEmail(),
        new Password(
            loginRequest.getPassword(),
            loginRequest.isEncryptedPassword() ? PasswordState.BASE64_ENCRYPTED : PasswordState.RAW));
  }

  public LoginResponse toDTO(String token) {
    return new LoginResponse(token);
  }
}
