package com.goms.application;

import com.goms.domain.shared.DomainException;

public interface UserManagementService {

  String generateToken(String email, String password, boolean passwordEncrypted)
      throws DomainException;

  Integer verifyTokenAndExtractUserIdFrom(String token) throws DomainException;
}
