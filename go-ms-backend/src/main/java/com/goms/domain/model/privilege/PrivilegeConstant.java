package com.goms.domain.model.privilege;

import java.util.HashMap;
import java.util.Map;

public enum PrivilegeConstant {
  OP_USERS_MANAGEMENT(1, "Users Management"),
  OP_BRANCHES_MANAGEMENT(2, "Branches Management"),
  OP_UNITS_MANAGEMENT(3, "Units Management");

  private final Integer id;
  private final String description;
  private static final Map<Integer, PrivilegeConstant> lookupOfPrivileges = new HashMap<>();

  static {
    for (PrivilegeConstant ov : PrivilegeConstant.values()) {
      lookupOfPrivileges.put(ov.getId(), ov);
    }
  }

  PrivilegeConstant(int id, String description) {
    this.id = id;
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public static PrivilegeConstant get(Integer id) {
    return lookupOfPrivileges.get(id);
  }
}
