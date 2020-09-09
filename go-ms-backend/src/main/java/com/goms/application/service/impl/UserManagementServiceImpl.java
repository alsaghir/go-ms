package com.goms.application.service.impl;

import com.goms.application.service.UserManagementService;
import com.goms.application.service.command.CreateUserCommand;
import com.goms.application.service.data.ProfileData;
import com.goms.application.service.data.UserDetailsData;
import com.goms.application.service.data.UserInfoData;
import com.goms.application.shared.ApplicationException;
import com.goms.domain.model.config.AppConfig;
import com.goms.domain.model.config.AppConfigRepository;
import com.goms.domain.model.profile.Profile;
import com.goms.domain.model.profile.ProfileRepository;
import com.goms.domain.model.user.Password;
import com.goms.domain.model.user.PasswordState;
import com.goms.domain.model.user.User;
import com.goms.domain.model.user.UserRepository;
import com.goms.domain.service.AuthService;
import com.goms.domain.service.CryptoService;
import com.goms.domain.service.EventService;
import com.goms.domain.shared.DomainError;
import com.goms.domain.shared.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserManagementServiceImpl implements UserManagementService {

  private final AppConfigRepository appConfigRepository;
  private final AuthService authService;
  private final CryptoService cryptoService;
  private final EventService eventService;
  private final ProfileRepository profileRepository;
  private final UserRepository userRepository;

  @Autowired
  public UserManagementServiceImpl(
      AppConfigRepository appConfigRepository,
      AuthService authService,
      CryptoService cryptoService,
      EventService eventService,
      ProfileRepository profileRepository,
      UserRepository userRepository) {

    this.appConfigRepository = appConfigRepository;
    this.authService = authService;
    this.cryptoService = cryptoService;
    this.eventService = eventService;
    this.profileRepository = profileRepository;
    this.userRepository = userRepository;
  }

  @Transactional(
      transactionManager = "transactionManager",
      rollbackFor = ApplicationException.class,
      readOnly = true)
  @Override
  public String generateToken(String email, String password, boolean passwordEncrypted)
      throws ApplicationException {
    try {

      AppConfig appConfig = this.appConfigRepository.findAll();
      String rawPassword =
          this.cryptoService.decryptedPassword(
              password, appConfig.rsaPrivateKey(), passwordEncrypted);

      User authenticatedUser = this.authService.authenticate(email, rawPassword);

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

  @Override
  public Set<UserInfoData> retrieveAllUsersInfo() {
    List<User> users = this.userRepository.findAll();

    return users.parallelStream()
        .map(user -> new UserInfoData(user.id(), user.email(), user.firstName(), user.lastName()))
        .collect(Collectors.toSet());
  }

  @Override
  public Set<ProfileData> retrieveProfilesAndPrivileges() {
    Set<Profile> profiles = this.profileRepository.findAll();

    return profiles.parallelStream()
        .map(
            profile ->
                new ProfileData()
                    .setId(profile.id())
                    .setName(profile.name())
                    .setPrivileges(
                        profile.privileges().parallelStream()
                            .map(privilege -> privilege.privilegeConstant().name())
                            .collect(Collectors.toSet())))
        .collect(Collectors.toSet());
  }

  @Override
  public void createNewUser(CreateUserCommand createUserCommand) throws ApplicationException {
    try {
      User createdUser =
          this.userRepository.saveFull(
              User.of(
                  createUserCommand.getEmail(),
                  Password.of("123", PasswordState.RAW),
                  false,
                  createUserCommand.getFirstName(),
                  createUserCommand.getLastName()));

      this.eventService.publish(
          createdUser.id(), new UserDetailsData().setEmail(createdUser.email()));

    } catch (DomainException domainException) {
      throw new ApplicationException(domainException);
    }
  }
}
