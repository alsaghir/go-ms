package com.goms.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goms.domain.model.profile.Profile;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "APP_USER")
public class User {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_SEQ_GENERATOR")
  @TableGenerator(
      name = "USER_SEQ_GENERATOR",
      table = "APP_SEQ_GENERATOR",
      pkColumnName = "SEQ_NAME",
      pkColumnValue = "USER_SEQ_PK",
      valueColumnName = "SEQ_VALUE",
      initialValue = 1,
      allocationSize = 1)
  private Integer id;

  @Column(name = "EMAIL")
  private String email;

  @JsonIgnore
  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "ACTIVE")
  private boolean active;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @ManyToMany
  @JoinTable(
      name = "APP_USER_PROFILE",
      joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
      inverseJoinColumns = @JoinColumn(name = "PROFILE_ID", referencedColumnName = "ID"))
  private Set<Profile> profiles;

  public User(String email, String password, boolean active, String firstName, String lastName, Set<Profile> profiles) {
    this.email = email;
    this.password = password;
    this.active = active;
    this.firstName = firstName;
    this.lastName = lastName;
    this.profiles = profiles;
  }

  public User() {}

  public Integer getId() {
    return id;
  }

  public User setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public boolean isActive() {
    return active;
  }

  public User setActive(boolean active) {
    this.active = active;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public User setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public User setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public Set<Profile> getProfiles() {
    return profiles;
  }

  public User setProfiles(Set<Profile> profiles) {
    this.profiles = profiles;
    return this;
  }
}
