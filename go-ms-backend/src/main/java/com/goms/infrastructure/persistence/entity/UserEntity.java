package com.goms.infrastructure.persistence.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "APP_USER")
public class UserEntity {

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
  private Set<ProfileEntity> profileEntitySet;

  UserEntity() {}

  public UserEntity(Integer id, String email, String password, boolean active, String firstName, String lastName) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.active = active;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Integer getId() {
    return id;
  }

  public UserEntity setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserEntity setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserEntity setPassword(String password) {
    this.password = password;
    return this;
  }

  public boolean isActive() {
    return active;
  }

  public UserEntity setActive(boolean active) {
    this.active = active;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public UserEntity setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public UserEntity setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public Set<ProfileEntity> getProfileEntitySet() {
    return profileEntitySet;
  }

  public UserEntity setProfileEntitySet(Set<ProfileEntity> profileEntitySet) {
    this.profileEntitySet = profileEntitySet;
    return this;
  }
}
