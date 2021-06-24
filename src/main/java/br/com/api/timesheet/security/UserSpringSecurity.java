package br.com.api.timesheet.security;

import br.com.api.timesheet.enumeration.ProfileEnum;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserSpringSecurity implements UserDetails {

  private String username;
  private String password;
  private String name;
  private Collection<? extends GrantedAuthority> authorities;

  public UserSpringSecurity(String username, String password, String name, ProfileEnum profile) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.authorities = Collections.singletonList(new SimpleGrantedAuthority(profile.toString()));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
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
    return true;
  }
}
