package com.goms.domain.service;

import com.goms.domain.model.privilege.Privilege;

import java.util.Set;

public interface UtilService {

    <T> boolean equals(Set<T> firstSet, Set<T> secondSet);

    Set<Privilege> differentPrivilegesBetween(Set<Privilege> allPrivileges, Set<Privilege> storedPrivileges);
}
