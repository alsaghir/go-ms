package com.goms.domain.model.user;

import com.goms.domain.model.profile.Profile;
import com.goms.domain.service.CryptoService;
import com.goms.domain.shared.DomainError;
import com.goms.domain.shared.DomainException;

import java.util.Set;

public class User {

  private Integer id;
  private final String email;
  private final Password password;
  private boolean active;
  private String firstName;
  private String lastName;
  private Set<Profile> profiles;

  private User(String email, Password password) {
    this.email = email;
    this.password = password;
  }

  public User(
      Integer id,
      String email,
      Password password,
      boolean active,
      String firstName,
      String lastName) {
    this(email, password, active);
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  private User(String email, Password password, boolean active) {
    this(email, password);
    this.active = active;
  }

  public static User of(
      String email, Password password, boolean active, String firstName, String lastName)
      throws DomainException {

    if (email == null || email.isBlank() || password == null)
      throw new DomainException(
          "Error instantiating user per input validation", DomainError.UNEXPECTED_ERROR);

    return new User(email, password, active).assignFirstName(firstName).assignLastName(lastName);
  }

  public Integer id() {
    return this.id;
  }

  public String email() {
    return this.email;
  }

  public String firstName() {
    return firstName;
  }

  public String lastName() {
    return lastName;
  }

  public Password password() {
    return password;
  }

  public User assignFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public User assignLastName(String lastName) {
    this.lastName = lastName;
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
