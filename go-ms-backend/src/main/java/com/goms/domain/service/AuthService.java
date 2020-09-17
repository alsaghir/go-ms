package com.goms.domain.service;

import com.goms.domain.model.user.User;
import com.goms.domain.shared.DomainException;

public interface AuthService {

  User authenticate(String email, String password) throws DomainException;
}
