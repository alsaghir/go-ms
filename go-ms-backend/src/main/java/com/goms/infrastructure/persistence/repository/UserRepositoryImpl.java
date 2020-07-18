package com.goms.infrastructure.persistence.repository;

import com.goms.domain.model.user.User;
import com.goms.domain.model.user.UserRepository;
import com.goms.infrastructure.persistence.converter.UserConverter;
import com.goms.infrastructure.persistence.entity.UserEntity;
import com.goms.infrastructure.persistence.repository.jpa.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final UserRepositoryJpa userRepositoryJpa;
  private final UserConverter userConverter;

  @Autowired
  public UserRepositoryImpl(UserRepositoryJpa userRepositoryJpa, UserConverter userConverter) {
    this.userRepositoryJpa = userRepositoryJpa;
    this.userConverter = userConverter;
  }

  @Override
  public Optional<User> findByEmailWithProfiles(String email) {
    Optional<UserEntity> optionalUsersEntity = this.userRepositoryJpa.findByEmail(email);
    return optionalUsersEntity.map(this.userConverter::toDomainWithProfiles);
  }

  @Override
  public Optional<User> findByIdWithProfiles(Integer id) {
    Optional<UserEntity> optionalUsersEntity = this.userRepositoryJpa.findById(id);
    return optionalUsersEntity.map(this.userConverter::toDomainWithProfiles);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    Optional<UserEntity> optionalUsersEntity = this.userRepositoryJpa.findByEmail(email);
    return optionalUsersEntity.map(this.userConverter::toDomain);
  }

  @Override
  public Optional<User> findById(Integer id) {
    Optional<UserEntity> optionalUsersEntity = this.userRepositoryJpa.findById(id);
    return optionalUsersEntity.map(this.userConverter::toDomain);
  }

  @Override
  public User saveFull(User user) {
    UserEntity userEntity = this.userConverter.toPersistenceEntity(user);
    UserEntity savedUserEntity = this.userRepositoryJpa.save(userEntity);
    return this.userConverter.toDomain(savedUserEntity);
  }

  @Override
  public Optional<User> findAny() {
    Optional<UserEntity> optionalUsersEntity = this.userRepositoryJpa.findTopBy();
    return optionalUsersEntity.map(this.userConverter::toDomain);
  }
}
