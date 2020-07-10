package com.goms.interfaces.usermanagement.facade.internal;


import com.goms.application.service.UserManagementService;
import com.goms.application.shared.ApplicationException;
import com.goms.domain.model.user.User;
import com.goms.interfaces.usermanagement.facade.UserManagementFacade;
import com.goms.interfaces.usermanagement.facade.dto.LoginRequest;
import com.goms.interfaces.usermanagement.facade.dto.LoginResponse;
import com.goms.interfaces.usermanagement.facade.internal.assembler.LoginDTOAssembler;
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
    LoginDTOAssembler assembler = new LoginDTOAssembler();
    User enteredUser = assembler.fromDTO(loginRequest);
    String token = userManagementService.generateToken(enteredUser);
    return assembler.toDTO(token);
  }

  @Override
  public Integer validateAndExtractUserIdFromToken(String token) throws ApplicationException {
    return userManagementService.verifyTokenAndExtractUserIdFrom(token);
  }
}
