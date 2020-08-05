package com.goms.interfaces.usermanagement.web;

import com.goms.application.service.data.UserDetailsData;
import com.goms.application.service.data.UserInfoData;
import com.goms.application.shared.ApplicationException;
import com.goms.infrastructure.utility.APIController;
import com.goms.interfaces.usermanagement.facade.UserManagementFacade;
import com.goms.interfaces.usermanagement.facade.dto.LoginRequest;
import com.goms.interfaces.usermanagement.facade.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@APIController
public class UserManagementController {

  private final UserManagementFacade userManagementFacade;

  @Autowired
  public UserManagementController(UserManagementFacade userManagementFacade) {
    this.userManagementFacade = userManagementFacade;
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest authRequest) throws ApplicationException {
    return this.userManagementFacade.generateToken(authRequest);
  }

  @GetMapping("/user")
  @PreAuthorize("isAuthenticated()")
  public UserDetailsData user() {
    return this.userManagementFacade.getUserDetails();
  }

  @GetMapping("/users")
  @PreAuthorize("isAuthenticated()")
  public Set<UserInfoData> users() {
    return this.userManagementFacade.getUsersInfo();
  }

  @GetMapping("/profiles")
  @PreAuthorize("isAuthenticated()")
  public Set<UserInfoData> profiles() {
    return this.userManagementFacade.getUsersInfo();
  }

  @GetMapping("/privileges")
  @PreAuthorize("isAuthenticated()")
  public Set<UserInfoData> privileges() {
    return this.userManagementFacade.getUsersInfo();
  }
}
