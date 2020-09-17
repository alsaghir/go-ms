package com.goms.application.impl;

import com.goms.application.UserManagementService;
import com.goms.domain.model.config.AppConfig;
import com.goms.domain.model.config.AppConfigRepository;
import com.goms.domain.model.user.User;
import com.goms.domain.model.user.UserRepository;
import com.goms.domain.service.AuthService;
import com.goms.domain.service.CryptoService;
import com.goms.domain.shared.DomainError;
import com.goms.domain.shared.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserManagementServiceImpl implements UserManagementService {

  private final AppConfigRepository appConfigRepository;
  private final AuthService authService;
  private final CryptoService cryptoService;
  private final UserRepository userRepository;

  @Autowired
  public UserManagementServiceImpl(
      AppConfigRepository appConfigRepository,
      AuthService authService,
      CryptoService cryptoService,
      UserRepository userRepository) {

    this.appConfigRepository = appConfigRepository;
    this.authService = authService;
    this.cryptoService = cryptoService;
    this.userRepository = userRepository;
  }

  @Transactional(
      transactionManager = "transactionManager",
      rollbackFor = DomainException.class,
      readOnly = true)
  @Override
  public String generateToken(String email, String password, boolean passwordEncrypted)
      throws DomainException {

    AppConfig appConfig = this.appConfigRepository.findAll();
    String rawPassword =
        this.cryptoService.decryptedPassword(
            password, appConfig.rsaPrivateKey(), passwordEncrypted);

    User authenticatedUser = this.authService.authenticate(email, rawPassword);

    return this.cryptoService.generateJwtTokenForUser(
        authenticatedUser.getId(), appConfig.jwtSigningKey(), appConfig.jwtExpirationTime());
  }

  @Override
  public Integer verifyTokenAndExtractUserIdFrom(String token) throws DomainException {
    AppConfig appConfig = this.appConfigRepository.findAll();
    boolean tokenVerified;

    tokenVerified = this.cryptoService.verifyToken(token, appConfig.jwtSigningKey());

    if (!tokenVerified)
      throw new DomainException("Unsuccessful token verification", DomainError.UNEXPECTED_ERROR);

    Integer userId;
    userId = this.cryptoService.getUserIdFrom(token);

    Optional<User> storedUser = userRepository.findById(userId);

    if (storedUser.isEmpty())
      throw new DomainException("UserId: " + userId + " not found", DomainError.UNEXPECTED_ERROR);

    return storedUser.get().getId();
  }
}
