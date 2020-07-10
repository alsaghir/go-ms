package com.goms.domain.model.profile;

import com.goms.domain.model.privilege.Privilege;

import java.util.Set;

public class Profile {
  private Integer id;
  private String name;
  private Set<Privilege> privileges;

  public Profile(String name) {
    this.name = name;
  }

  public Profile(Integer id, String name) {
    this(name);
    this.id = id;
  }

  public Integer id() {
    return id;
  }

  public Profile specifyAnId(Integer id) {
    this.id = id;
    return this;
  }

  public String name() {
    return name;
  }

  public Profile setName(String name) {
    this.name = name;
    return this;
  }

  public Set<Privilege> privileges() {
    return privileges;
  }

  public Profile assignPrivileges(Set<Privilege> privileges) {
    this.privileges = privileges;
    return this;
  }
}
