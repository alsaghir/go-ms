package com.goms.interfaces.web.usermanagement;

import com.goms.application.service.UserManagementService;
import com.goms.application.service.command.CreateUserCommand;
import com.goms.application.service.command.GenerateJwtTokenCommand;
import com.goms.application.service.data.UserDetailsData;
import com.goms.application.service.data.UserInfoData;
import com.goms.application.shared.ApplicationException;
import com.goms.infrastructure.utility.APIController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@APIController
public class UserManagementController {

  private final UserManagementService userManagementService;

  @Autowired
  public UserManagementController(UserManagementService userManagementService) {
    this.userManagementService = userManagementService;
  }

  @PostMapping(
      value = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String login(@RequestBody GenerateJwtTokenCommand generateJwtTokenCommand) throws ApplicationException {
    return this.userManagementService.generateToken(
        generateJwtTokenCommand.getEmail(),
        generateJwtTokenCommand.getPassword(),
        generateJwtTokenCommand.isPasswordEncrypted());
  }

  @GetMapping("/user")
  @PreAuthorize("isAuthenticated()")
  public UserDetailsData user() {
    return this.userManagementService.retrieveUserDetails();
  }

  @PostMapping("/user")
  @PreAuthorize("isAuthenticated()")
  public void user(@RequestBody CreateUserCommand createUserCommand) throws ApplicationException {
    this.userManagementService.createNewUser(createUserCommand);
  }

  @GetMapping("/users")
  @PreAuthorize("isAuthenticated()")
  public Set<UserInfoData> users() {
    return this.userManagementService.retrieveAllUsersInfo();
  }

  @GetMapping("/profiles")
  @PreAuthorize("isAuthenticated()")
  public Set<UserInfoData> profiles() {
    return this.userManagementService.retrieveAllUsersInfo();
  }

  @GetMapping("/privileges")
  @PreAuthorize("isAuthenticated()")
  public Set<UserInfoData> privileges() {
    return this.userManagementService.retrieveAllUsersInfo();
  }
}
