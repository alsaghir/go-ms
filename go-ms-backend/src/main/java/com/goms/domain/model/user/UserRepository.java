package com.goms.domain.model.user;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmailWithProfiles(String userName);
    Optional<User> findByIdWithProfiles(Integer id);

    User save(User user);

    Optional<User> findAny();

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

}
