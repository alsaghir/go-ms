package com.goms.infrastructure.persistence.repository.jpa;

import com.goms.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJpa
    extends JpaRepository<UserEntity, Integer>, QueryByExampleExecutor<UserEntity> {
  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findTopBy();
}
