package com.goms.application.impl;

import com.goms.domain.model.privilege.Privilege;
import com.goms.domain.service.UtilService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UtilServiceImpl implements UtilService {

    @Override
    public <T> boolean equals(Set<T> firstSet, Set<T> secondSet) {
        return firstSet.equals(secondSet);
    }

    @Override
    public Set<Privilege> differentPrivilegesBetween(Set<Privilege> allPrivileges, Set<Privilege> storedPrivileges) {
        allPrivileges.removeAll(storedPrivileges);
        return allPrivileges;
    }
}
