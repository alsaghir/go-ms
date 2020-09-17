package com.goms.application.data;

public class ProfileData {
  private Integer id;
  private String name;

  public Integer getId() {
    return id;
  }

  public ProfileData setId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public ProfileData setName(String name) {
    this.name = name;
    return this;
  }
}
