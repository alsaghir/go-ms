package com.goms.domain.service;

import com.goms.domain.model.user.User;
import com.goms.domain.shared.DomainException;

public interface AuthService {
  void authenticateUsingNameAndPassword(User user) throws DomainException;
}
