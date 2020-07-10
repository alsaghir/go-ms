package com.goms.interfaces.usermanagement.facade.dto;

public class LoginRequest {
  private String email;
  private String password;
  private boolean encryptedPassword = true;

  public String getPassword() {
    return password;
  }

  public LoginRequest setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public LoginRequest setEmail(String email) {
    this.email = email;
    return this;
  }

  public boolean isEncryptedPassword() {
    return encryptedPassword;
  }

  public void setEncryptedPassword(boolean encryptedPassword) {
    this.encryptedPassword = encryptedPassword;
  }
}
