package com.goms.infrastructure.auth;

import com.goms.domain.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

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

    public User getUser() {
        return this.user;
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
