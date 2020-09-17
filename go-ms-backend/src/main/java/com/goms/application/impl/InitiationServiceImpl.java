package com.goms.application.impl;

import com.goms.application.InitiationService;
import com.goms.domain.model.config.AppConfigRepository;
import com.goms.domain.model.privilege.Privilege;
import com.goms.domain.model.privilege.PrivilegeConstant;
import com.goms.domain.model.privilege.PrivilegeRepository;
import com.goms.domain.model.profile.Profile;
import com.goms.domain.model.profile.ProfileRepository;
import com.goms.domain.model.user.User;
import com.goms.domain.model.user.UserRepository;
import com.goms.domain.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InitiationServiceImpl implements InitiationService {

  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;
  private final AppConfigRepository appConfigRepository;
  private final PrivilegeRepository privilegeRepository;
  private final UtilService utilService;

  @Autowired
  public InitiationServiceImpl(
      UserRepository userRepository,
      ProfileRepository profileRepository,
      AppConfigRepository appConfigRepository,
      PrivilegeRepository privilegeRepository,
      UtilService utilService) {
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
    this.appConfigRepository = appConfigRepository;
    this.privilegeRepository = privilegeRepository;
    this.utilService = utilService;
  }

  @Override
  @Transactional(transactionManager = "transactionManager")
  public void storeDefaultData() {
    if (initializationIsRequired()) {
      this.initializePrivileges();
      this.initializeProfilesAndUsers();
    }
  }

  private void initializeProfilesAndUsers() {
    if (this.userRepository.findTopBy().isEmpty()) {

      Profile defaultProfile =
          this.profileRepository.save(
              new Profile("admin", new HashSet<>(this.privilegeRepository.findAll()), null));

      this.userRepository.save(
          new User(
              "admin@example.com", "{noop}admin", true, "admin", "admin", Set.of(defaultProfile)));
    }
  }

  private void initializePrivileges() {
    Set<Privilege> storedPrivileges = this.privilegeRepository.findAllBy();
    Set<Privilege> allPrivileges =
        Set.of(PrivilegeConstant.values()).parallelStream()
            .map(Privilege::new)
            .collect(Collectors.toSet());

    if (!utilService.equals(allPrivileges, storedPrivileges)) {
      Set<Privilege> notStoredPrivileges =
          utilService.differentPrivilegesBetween(allPrivileges, storedPrivileges);
      this.privilegeRepository.saveAll(notStoredPrivileges);
    }
  }

  private boolean initializationIsRequired() {
    return this.appConfigRepository.findAll().initializingDataRequired();
  }
}
