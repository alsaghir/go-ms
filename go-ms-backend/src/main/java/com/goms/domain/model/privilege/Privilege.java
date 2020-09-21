package com.goms.domain.model.privilege;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "APP_PRIVILEGE")
public class Privilege {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "PRIVILEGE")
  @Enumerated(EnumType.STRING)
  private PrivilegeConstant privilege;

  @Column(name= "DESCRIPTION")
  private String description;

  public Privilege() {
  }

  public Privilege(PrivilegeConstant privilege) {
    this.id = privilege.getId();
    this.privilege = privilege;
    this.description = privilege.getDescription();
  }

  public Integer getId() {
    return this.id;
  }

  public Privilege setId(Integer id) {
    this.id = id;
    setPrivilege(PrivilegeConstant.get(id));
    setDescription(this.privilege.getDescription());
    return this;
  }

  public PrivilegeConstant getPrivilege() {
    return privilege;
  }

  public Privilege setPrivilege(PrivilegeConstant privilege) {
    this.privilege = privilege;
    this.id = privilege.getId();
    setDescription(privilege.getDescription());
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Privilege setDescription(String description) {
    this.description = description;
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Privilege)) {
      return false;
    }

    Privilege that = (Privilege) obj;
    return that.getId().equals(this.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }
}
