package com.goms.infrastructure.persistence.entity;


import com.goms.domain.model.privilege.PrivilegeConstant;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "APP_PRIVILEGE")
public class PrivilegeEntity {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "PRIVILEGE")
  @Enumerated(EnumType.STRING)
  private PrivilegeConstant privilege;

  public PrivilegeEntity() {
  }

  public PrivilegeEntity(PrivilegeConstant privilege) {
    this.id = privilege.getId();
    this.privilege = privilege;
  }

  public Integer getId() {
    return this.id;
  }

  public PrivilegeEntity setId(Integer id) {
    this.id = id;
    this.privilege = PrivilegeConstant.get(id);
    return this;
  }

  public PrivilegeConstant getPrivilege() {
    return privilege;
  }

  public PrivilegeEntity setPrivilege(PrivilegeConstant privilege) {
    this.privilege = privilege;
    this.id = privilege.getId();
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof PrivilegeEntity)) {
      return false;
    }

    PrivilegeEntity that = (PrivilegeEntity) obj;
    return that.getId().equals(this.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }
}
