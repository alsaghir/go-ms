package com.goms.application.service;

import com.goms.application.service.command.CreateUserCommand;
import com.goms.application.service.data.ProfileData;
import com.goms.application.service.data.UserDetailsData;
import com.goms.application.service.data.UserInfoData;
import com.goms.application.shared.ApplicationException;

import java.util.Set;

public interface UserManagementService {

  String generateToken(String email, String password, boolean passwordEncrypted)
      throws ApplicationException;

  Integer verifyTokenAndExtractUserIdFrom(String token) throws ApplicationException;

  UserDetailsData retrieveUserDetails();

  Set<UserInfoData> retrieveAllUsersInfo();

  Set<ProfileData> profilesAndPrivileges();

  void createNewUser(CreateUserCommand createUserCommand) throws ApplicationException;
}
