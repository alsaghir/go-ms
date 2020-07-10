package com.goms.domain.model.user;

import com.goms.domain.model.profile.Profile;
import com.goms.domain.service.CryptoService;
import com.goms.domain.shared.DomainException;

import java.util.Set;

;

public class User {

  private Integer id;
  private String email;
  private Password password;
  private boolean active;
  private Set<Profile> profiles;

  public User(String email, Password password) {
    this.email = email;
    this.password = password;
  }

  public User(Integer id, String email, Password password, boolean active) {
    this(email, password, active);
    this.id = id;;
  }

  public User(String email, Password password, boolean active) {
    this(email, password);
    this.active = active;
  }

  public Integer id() {
    return this.id;
  }

  public String email(){
    return this.email;
  }

  public Password password() {
    return password;
  }

  public User assignPassword(Password password) {
    this.password = password;
    return this;
  }

  public boolean isActive() {
    return this.active;
  }

  public Set<Profile> profiles() {
    return profiles;
  }

  public User assignProfiles(Set<Profile> profiles) {
    this.profiles = profiles;
    return this;
  }

  public String generateToken(
          CryptoService cryptoService, String jwtSigningKey, String jwtExpirationTime)
          throws DomainException {
    return cryptoService.generateJwtTokenForUser(this.id, jwtSigningKey, jwtExpirationTime);
  }

}
