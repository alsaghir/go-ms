package com.goms.domain.model.privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RepositoryRestResource
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

  @RestResource
  Set<Privilege> findAllBy();
}
