package com.goms.domain.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer>, QueryByExampleExecutor<User> {
  Optional<User> findByEmail(String email);

  Optional<User> findTopBy();
}
