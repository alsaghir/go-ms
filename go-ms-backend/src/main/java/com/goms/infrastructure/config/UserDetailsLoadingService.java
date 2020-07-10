package com.goms.infrastructure.config;

import com.goms.domain.model.user.User;
import com.goms.domain.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
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

  // For spring user representation.
  // Shouldn't be used except with Spring Security Authentication
  private static class UserPrincipal implements UserDetails {

    private final User user;
    private final Set<SimpleGrantedAuthority> grantedAuthorities;

    public UserPrincipal(User user) {
      super();
      this.user = user;
      this.grantedAuthorities =
          this.user.profiles().stream()
              .flatMap(profile -> profile.privileges().stream())
              .map(privilege -> new SimpleGrantedAuthority(privilege.privilegeConstant().name()))
              .collect(Collectors.toSet());
    }

    public Integer getId() {
      return this.user.id();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
      return this.user.password().value();
    }

    @Override
    public String getUsername() {
      return this.user.email();
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return this.user.isActive();
    }
  }
}
