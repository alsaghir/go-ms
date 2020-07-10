package com.goms.domain.model.profile;

import java.util.Optional;

public interface ProfileRepository {
  Optional<Profile> findAny();
  Profile save(Profile profile);
}
