package com.goms.application.service;

import com.goms.application.service.data.UserDetailsData;
import com.goms.application.shared.ApplicationException;
import com.goms.domain.model.user.User;

public interface UserManagementService {

  String generateToken(String email, String password, boolean passwordEncrypted)
      throws ApplicationException;

  Integer verifyTokenAndExtractUserIdFrom(String token) throws ApplicationException;


  UserDetailsData retrieveUserDetails();
}
