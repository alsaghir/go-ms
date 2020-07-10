package com.goms.infrastructure.persistence.repository.jpa;

import com.goms.infrastructure.persistence.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PrivilegeRepositoryJpa extends JpaRepository<PrivilegeEntity, Integer> {

    Set<PrivilegeEntity> findAllBy();
}
