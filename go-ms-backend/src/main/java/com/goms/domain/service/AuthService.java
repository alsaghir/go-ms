package com.goms.domain.service;

import com.goms.domain.model.user.User;
import com.goms.domain.shared.DomainException;

public interface AuthService {
  User authenticateUsingNameAndPassword(User user) throws DomainException;
}
