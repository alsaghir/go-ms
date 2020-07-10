package com.goms.domain.service;

import com.goms.domain.shared.DomainException;

public interface CryptoService {
  String decryptedPassword(final String encryptedMessage, final String rsaPrivateKey)
      throws DomainException;

  String generateJwtTokenForUser(Integer id, String jwtSigningKey, String jwtExpirationTime)
      throws DomainException;

    Integer getUserIdFrom(String token) throws DomainException;

  boolean verifyToken(String token, String secret) throws DomainException;
}
