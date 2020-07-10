package com.goms.infrastructure.persistence.repository.jpa;

import com.goms.infrastructure.persistence.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepositoryJpa extends JpaRepository<ProfileEntity, Integer> {
  Optional<ProfileEntity> findTopBy();
}
