package com.goms.domain.model.privilege;

import java.util.Set;

public interface PrivilegeRepository {
    Set<Privilege> findAllStored();
    Set<Privilege> findAll();
    Set<Privilege> save(Set<Privilege> notStoredPrivileges);
}
