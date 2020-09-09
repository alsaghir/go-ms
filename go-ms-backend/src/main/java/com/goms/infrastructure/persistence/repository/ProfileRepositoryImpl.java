package com.goms.infrastructure.persistence.repository;

import com.goms.domain.model.profile.Profile;
import com.goms.domain.model.profile.ProfileRepository;
import com.goms.infrastructure.persistence.converter.ProfileConverter;
import com.goms.infrastructure.persistence.entity.ProfileEntity;
import com.goms.infrastructure.persistence.repository.jpa.ProfileRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

  private final ProfileRepositoryJpa profileRepositoryJpa;
  private final ProfileConverter profileConverter;

  @Autowired
  public ProfileRepositoryImpl(
      ProfileRepositoryJpa profileRepositoryJpa, ProfileConverter profileConverter) {
    this.profileRepositoryJpa = profileRepositoryJpa;
    this.profileConverter = profileConverter;
  }

  @Override
  public Profile save(Profile profile) {
    ProfileEntity savedProfileEntity =
        this.profileRepositoryJpa.save(this.profileConverter.toPersistenceEntity(profile));
    return this.profileConverter.toFullDomain(savedProfileEntity);
  }

  @Override
  public Set<Profile> findAll() {
    List<ProfileEntity> profileEntities = this.profileRepositoryJpa.findAll();
    return this.profileConverter.toFullDomainSet(profileEntities);
  }

  @Override
  public Optional<Profile> findById(Integer id) {
    Optional<ProfileEntity> profile = this.profileRepositoryJpa.findById(id);
    return profile.map(this.profileConverter::toFullDomain);
  }
}
