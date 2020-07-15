package com.goms.application.service;


import com.goms.application.service.command.GenerateJwtTokenCommand;
import com.goms.application.shared.ApplicationException;
import com.goms.domain.model.user.User;

public interface UserManagementService {

  String generateToken(GenerateJwtTokenCommand generateJwtTokenCommand) throws ApplicationException;

  Integer verifyTokenAndExtractUserIdFrom(String token) throws ApplicationException;
}
