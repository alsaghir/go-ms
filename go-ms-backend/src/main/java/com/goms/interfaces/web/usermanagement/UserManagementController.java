package com.goms.interfaces.web.usermanagement;

import com.goms.application.UserManagementService;
import com.goms.application.command.GenerateJwtTokenCommand;
import com.goms.domain.shared.DomainException;
import com.goms.infrastructure.utility.APIController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@APIController
public class UserManagementController {

  private final UserManagementService userManagementService;

  @Autowired
  public UserManagementController(UserManagementService userManagementService) {
    this.userManagementService = userManagementService;
  }

  @PostMapping(
      value = "/jwt",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public HashMap<String, String> login(@RequestBody GenerateJwtTokenCommand generateJwtTokenCommand)
      throws DomainException {
    HashMap<String, String> response = new HashMap<>();
    response.put(
        "token",
        this.userManagementService.generateToken(
            generateJwtTokenCommand.getEmail(),
            generateJwtTokenCommand.getPassword(),
            generateJwtTokenCommand.isPasswordEncrypted()));
    return response;
  }
}
