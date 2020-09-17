package com.goms.domain.model.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
  Optional<Profile> findTopBy();

}
