package com.goms.domain.model.user;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmailWithProfiles(String userName);
    Optional<User> findByIdWithProfilesAndPrivileges(Integer id);

    User saveFull(User user);

    Optional<User> findAny();

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    boolean atLeastOneUserExists();
}
