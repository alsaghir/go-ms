package com.goms.infrastructure.config;

import com.goms.domain.model.user.User;
import com.goms.domain.model.user.UserRepository;
import com.goms.infrastructure.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager = "transactionManager")
@Service
public class UserDetailsLoadingService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserDetailsLoadingService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user =
        this.userRepository
            .findByEmailWithProfiles(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("Email: " + email + " not found"));

    return new UserPrincipal(user);
  }

  public UserDetails loadUserByUserId(Integer id) throws UsernameNotFoundException {
    User user =
        this.userRepository
            .findByIdWithProfiles(id)
            .orElseThrow(() -> new UsernameNotFoundException("User with id: " + id + " not found"));

    return new UserPrincipal(user);
  }
}
