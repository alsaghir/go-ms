package com.goms.domain.model.profile;

import com.goms.domain.model.user.User;
import com.goms.domain.model.privilege.Privilege;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "APP_PROFILE")
public class Profile {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PROFILE_SEQ_GENERATOR")
  @TableGenerator(
      name = "PROFILE_SEQ_GENERATOR", table = "APP_SEQ_GENERATOR", pkColumnName = "SEQ_NAME",
      pkColumnValue = "PROFILE_SEQ_PK", valueColumnName = "SEQ_VALUE", initialValue = 1,
      allocationSize = 1
  )
  private Integer id;

  @Column(name = "PROFILE_NAME")
  private String name;

  @ManyToMany
  @JoinTable(
      name = "APP_PROFILE_PRIVILEGE",
      joinColumns = @JoinColumn(name = "PROFILE_ID", referencedColumnName = "ID"),
      inverseJoinColumns = @JoinColumn(name = "OP_ID", referencedColumnName = "ID")
  )
  private Set<Privilege> privileges;

  @ManyToMany(mappedBy = "profiles")
  private Set<User> users;

  public Profile() { }

  public Profile(String name, Set<Privilege> privileges, Set<User> users) {
    this.name = name;
    this.privileges = privileges;
    this.users = users;
  }

  public Integer getId() {
    return this.id;
  }

  public Profile setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Profile setName(String name) {
    this.name = name;
    return this;
  }

  public Set<Privilege> getPrivileges() {
    return privileges;
  }

  public Profile setPrivileges(Set<Privilege> privileges) {
    this.privileges = privileges;
    return this;
  }

  public Set<User> getUsers() {
    return users;
  }

  public Profile setUsers(Set<User> users) {
    this.users = users;
    return this;
  }
}
