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
    return new UserEntity()
        .setId(user.id())
        .setPassword(user.password().value())
        .setEmail(user.email())
        .setActive(user.isActive());
  }

  public User toDomain(UserEntity userEntity) {
    return new User(
            userEntity.getId(),
            userEntity.getEmail(),
        new Password(userEntity.getPassword(), PasswordState.HASHED),
            userEntity.isActive()
    );
  }

  public User toDomainWithProfiles(UserEntity userEntity) {
    return new User(
            userEntity.getId(),
            userEntity.getEmail(),
            new Password(userEntity.getPassword(), PasswordState.HASHED),
            userEntity.isActive()
    )
            .assignProfiles(
                    userEntity.getProfileEntitySet() == null
                            ? null
                            : this.profileConverter.toDomainSet(userEntity.getProfileEntitySet()));
  }
}
