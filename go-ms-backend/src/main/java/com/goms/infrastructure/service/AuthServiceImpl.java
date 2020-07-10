package com.goms.infrastructure.service;

import com.goms.domain.model.user.User;
import com.goms.domain.service.AuthService;
import com.goms.domain.shared.DomainError;
import com.goms.domain.shared.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthServiceImpl(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public void authenticateUsingNameAndPassword(User user) throws DomainException {
    try {
      this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(user.email(), user.password().value()));
    } catch (BadCredentialsException ex) {
      throw new DomainException("Bad credentials Entered", ex, DomainError.BAD_CREDENTIALS);
    }
  }
}
