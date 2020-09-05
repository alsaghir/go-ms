package com.goms.domain.model.profile;

import java.util.Set;

public interface ProfileRepository {
  Profile save(Profile profile);

  Set<Profile> findAll();
}
