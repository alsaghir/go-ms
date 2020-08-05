package com.goms.infrastructure.service;

import com.goms.domain.model.user.Password;
import com.goms.domain.model.user.User;
import com.goms.domain.service.AuthService;
import com.goms.domain.shared.DomainError;
import com.goms.domain.shared.DomainException;
import com.goms.infrastructure.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthServiceImpl(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public User authenticateUsingNameAndPassword(User user) throws DomainException {
    try {
      Authentication auth =
          this.authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(user.email(), user.password().value()));
      return ((UserPrincipal) auth.getPrincipal()).getUserWithProfilesAndPrivileges();
    } catch (BadCredentialsException ex) {
      throw new DomainException("Bad credentials Entered", ex, DomainError.BAD_CREDENTIALS);
    }
  }

  @Override
  public User getAuthenticatedUserWithProfilesAndPrivileges() {
    UserPrincipal userPrincipal =
        (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userPrincipal.getUserWithProfilesAndPrivileges();
  }

  @Override
  public User authenticate(String email, String password) throws DomainException {
    try {
      Authentication auth =
              this.authenticationManager.authenticate(
                      new UsernamePasswordAuthenticationToken(email, password));
      return ((UserPrincipal) auth.getPrincipal()).getUserWithProfilesAndPrivileges();
    } catch (BadCredentialsException ex) {
      throw new DomainException("Bad credentials Entered", ex, DomainError.BAD_CREDENTIALS);
    }
  }
}
