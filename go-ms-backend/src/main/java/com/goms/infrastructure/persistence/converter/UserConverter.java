package com.goms.infrastructure.persistence.converter;

import com.goms.domain.model.user.Password;
import com.goms.domain.model.user.PasswordState;
import com.goms.domain.model.user.User;
import com.goms.infrastructure.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

  private final ProfileConverter profileConverter;

  @Autowired
  public UserConverter(ProfileConverter profileConverter) {
    this.profileConverter = profileConverter;
  }

  public UserEntity toPersistenceEntity(User user) {
    return new UserEntity(
            user.id(),
            user.email(),
            user.password().value(),
            user.isActive(),
            user.firstName(),
            user.lastName())
        .setProfileEntitySet(
            user.profiles() == null
                ? null
                : this.profileConverter.toPersistenceEntitySet(user.profiles()));
  }

  public User toDomain(UserEntity userEntity) {
    return new User(
        userEntity.getId(),
        userEntity.getEmail(),
        new Password(userEntity.getPassword(), PasswordState.HASHED),
        userEntity.isActive(),
        userEntity.getFirstName(),
        userEntity.getLastName());
  }

  public User toDomainWithProfiles(UserEntity userEntity) {
    return new User(
            userEntity.getId(),
            userEntity.getEmail(),
            new Password(userEntity.getPassword(), PasswordState.HASHED),
            userEntity.isActive(),
            userEntity.getFirstName(),
            userEntity.getLastName())
        .assignProfiles(
            userEntity.getProfileEntitySet() == null
                ? null
                : this.profileConverter.toFullDomainSet(userEntity.getProfileEntitySet()));
  }
}
