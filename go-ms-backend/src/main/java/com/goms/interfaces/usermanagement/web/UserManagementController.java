package com.goms.interfaces.usermanagement.web;

import com.goms.application.service.data.UserDetailsData;
import com.goms.application.shared.ApplicationException;
import com.goms.infrastructure.utility.APIController;
import com.goms.interfaces.usermanagement.facade.UserManagementFacade;
import com.goms.interfaces.usermanagement.facade.dto.LoginRequest;
import com.goms.interfaces.usermanagement.facade.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
  public UserDetailsData user() {
    return this.userManagementFacade.getUserDetails();
  }
}
