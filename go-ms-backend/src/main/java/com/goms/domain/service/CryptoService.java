package com.goms.domain.service;

import com.goms.domain.shared.DomainException;

public interface CryptoService {
  String decryptedPassword(final String encryptedMessage, final String rsaPrivateKey)
      throws DomainException;

  String decryptedPassword(
      final String encryptedMessage, final String rsaPrivateKey, final boolean passwordEncrypted)
      throws DomainException;

  String generateJwtTokenForUser(
      final Integer id, final String jwtSigningKey, final String jwtExpirationTime)
      throws DomainException;

  Integer getUserIdFrom(final String token) throws DomainException;

  boolean verifyToken(final String token, final String secret) throws DomainException;
}
