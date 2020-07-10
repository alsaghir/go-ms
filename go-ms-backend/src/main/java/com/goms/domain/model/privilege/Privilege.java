package com.goms.domain.model.privilege;

import java.util.Objects;

public class Privilege {

  private final PrivilegeConstant privilegeConstant;

  public Privilege(PrivilegeConstant privilegeConstant) {
    this.privilegeConstant = privilegeConstant;
  }

  public PrivilegeConstant privilegeConstant() {
    return this.privilegeConstant;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Privilege)) return false;

    Privilege that = (Privilege) obj;
    return that.privilegeConstant().equals(this.privilegeConstant());
  }

  @Override
  public int hashCode() {
    return Objects.hash(privilegeConstant);
  }
}
