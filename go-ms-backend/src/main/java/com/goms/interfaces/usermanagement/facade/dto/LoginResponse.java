package com.goms.interfaces.usermanagement.facade.dto;

public class LoginResponse {
  private String token;

  public LoginResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public LoginResponse setToken(String token) {
    this.token = token;
    return this;
  }
}
