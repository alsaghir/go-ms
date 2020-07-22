package com.goms.application.service.impl;

import com.goms.application.service.UserManagementService;
import com.goms.application.service.data.ProfileData;
import com.goms.application.service.data.UserDetailsData;
import com.goms.application.shared.ApplicationException;
import com.goms.domain.model.config.AppConfig;
import com.goms.domain.model.config.AppConfigRepository;
import com.goms.domain.model.user.Password;
import com.goms.domain.model.user.PasswordState;
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
import java.util.stream.Collectors;

@Service
public class UserManagementServiceImpl implements UserManagementService {

  private final UserRepository userRepository;
  private final AppConfigRepository appConfigRepository;
  private final CryptoService cryptoService;
  private final AuthService authService;

  @Autowired
  public UserManagementServiceImpl(
      UserRepository userRepository,
      AppConfigRepository appConfigRepository,
      CryptoService cryptoService,
      AuthService authService) {
    this.userRepository = userRepository;
    this.appConfigRepository = appConfigRepository;
    this.cryptoService = cryptoService;
    this.authService = authService;
  }

  @Transactional(rollbackFor = ApplicationException.class, readOnly = true)
  @Override
  public String generateToken(String email, String password, boolean passwordEncrypted)
      throws ApplicationException {
    try {

      AppConfig appConfig = this.appConfigRepository.findAll();
      password =
          passwordEncrypted
              ? this.cryptoService.decryptedPassword(password, appConfig.rsaPrivateKey())
              : password;

      User userToAuthenticate = User.of(email, Password.of(password, PasswordState.RAW));

      User authenticatedUser =
          this.authService.authenticateUsingNameAndPassword(userToAuthenticate);

      return authenticatedUser.generateToken(
          this.cryptoService, appConfig.jwtSigningKey(), appConfig.jwtExpirationTime());

    } catch (DomainException domainException) {
      throw new ApplicationException(domainException);
    }
  }

  @Override
  public Integer verifyTokenAndExtractUserIdFrom(String token) throws ApplicationException {
    AppConfig appConfig = this.appConfigRepository.findAll();
    boolean tokenVerified;
    try {
      tokenVerified = this.cryptoService.verifyToken(token, appConfig.jwtSigningKey());
    } catch (DomainException domainException) {
      throw new ApplicationException(domainException);
    }

    if (!tokenVerified)
      throw new ApplicationException(
          new DomainException("Unsuccessful token verification", DomainError.UNEXPECTED_ERROR));

    Integer userId;
    try {
      userId = this.cryptoService.getUserIdFrom(token);
    } catch (DomainException domainException) {
      throw new ApplicationException(domainException);
    }

    Optional<User> storedUser = userRepository.findById(userId);

    if (storedUser.isEmpty())
      throw new ApplicationException(
          new DomainException("UserId: " + userId + " not found", DomainError.UNEXPECTED_ERROR));

    return storedUser.get().id();
  }

  @Override
  public UserDetailsData retrieveUserDetails() {

    User user = this.authService.getAuthenticatedUserWithProfilesAndPrivileges();

    return new UserDetailsData()
        .setEmail(user.email())
        .setFirstName(user.firstName())
        .setLastName(user.lastName())
        .setProfileDataSet(
            user.profiles().stream()
                .map(
                    profile ->
                        new ProfileData()
                            .setId(profile.id())
                            .setName(profile.name())
                            .setPrivileges(
                                profile.privileges().stream()
                                    .map(privilege -> privilege.privilegeConstant().name())
                                    .collect(Collectors.toSet())))
                .collect(Collectors.toSet()));
  }
}
