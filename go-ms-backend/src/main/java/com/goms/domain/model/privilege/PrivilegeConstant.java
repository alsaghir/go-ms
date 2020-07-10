package com.goms.domain.model.privilege;

import java.util.HashMap;
import java.util.Map;

public enum PrivilegeConstant {
  OP_CREATE_USER(1),
  OP_DELETE_ACCOUNT(2),
  OP_UPDATE_ACCOUNT(3),
  OP_READ_ACCOUNT(4);

  private final Integer id;
  private static final Map<Integer, PrivilegeConstant> lookupOfPrivileges = new HashMap<>();

  static {
    for (PrivilegeConstant ov : PrivilegeConstant.values()) {
      lookupOfPrivileges.put(ov.getId(), ov);
    }
  }

  PrivilegeConstant(int id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public static PrivilegeConstant get(Integer id) {
    return lookupOfPrivileges.get(id);
  }
}
