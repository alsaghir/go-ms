package com.goms.interfaces.usermanagement.facade.internal;

import com.goms.application.service.UserManagementService;
import com.goms.application.service.data.UserDetailsData;
import com.goms.application.shared.ApplicationException;
import com.goms.infrastructure.auth.UserPrincipal;
import com.goms.interfaces.usermanagement.facade.UserManagementFacade;
import com.goms.interfaces.usermanagement.facade.dto.LoginRequest;
import com.goms.interfaces.usermanagement.facade.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManagementFacadeImpl implements UserManagementFacade {

  private final UserManagementService userManagementService;

  @Autowired
  public UserManagementFacadeImpl(UserManagementService userManagementService) {
    this.userManagementService = userManagementService;
  }

  @Override
  public LoginResponse generateToken(LoginRequest loginRequest) throws ApplicationException {
    String token =
        userManagementService.generateToken(
                loginRequest.getEmail(),
                loginRequest.getPassword(),
                loginRequest.isEncryptedPassword());
    return new LoginResponse(token);
  }

  @Override
  public Integer validateAndExtractUserIdFromToken(String token) throws ApplicationException {
    return userManagementService.verifyTokenAndExtractUserIdFrom(token);
  }

  @Override
  public UserDetailsData getUserDetails() {
    return this.userManagementService.retrieveUserDetails();
  }


}
