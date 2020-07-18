package com.goms.application.service.impl;


import com.goms.application.service.InitiationService;
import com.goms.domain.model.config.AppConfigRepository;
import com.goms.domain.model.privilege.Privilege;
import com.goms.domain.model.privilege.PrivilegeRepository;
import com.goms.domain.model.profile.Profile;
import com.goms.domain.model.profile.ProfileRepository;
import com.goms.domain.model.user.Password;
import com.goms.domain.model.user.PasswordState;
import com.goms.domain.model.user.User;
import com.goms.domain.model.user.UserRepository;
import com.goms.domain.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
  @Transactional
  public void storeDefaultData() {
    if (checkIfInitializationIsRequired()) {
      initializePrivileges();
      initializeProfilesAndUsers();
    }
  }

  private void initializeProfilesAndUsers() {
    if (this.profileRepository.findAny().isEmpty()
            && this.userRepository.findAny().isEmpty()) {

      Profile defaultProfile =
          this.profileRepository.save(
              new Profile("admin").assignPrivileges(this.privilegeRepository.findAll()));

      this.userRepository.saveFull(
          new User("admin@example.com", new Password("{noop}admin", PasswordState.RAW), true)
                  .assignProfiles(Set.of(defaultProfile)));
    }
  }

  private void initializePrivileges() {
    Set<Privilege> storedPrivileges = this.privilegeRepository.findAllStored();
    Set<Privilege> allPrivileges = this.privilegeRepository.findAll();

    if (!utilService.equals(allPrivileges, storedPrivileges)) {
      Set<Privilege> notStoredPrivileges =
              utilService.differentPrivilegesBetween(allPrivileges, storedPrivileges);
      this.privilegeRepository.save(notStoredPrivileges);
    }
  }

  private boolean checkIfInitializationIsRequired() {
    return this.appConfigRepository.findAll().initializingDataRequired();
  }
}
