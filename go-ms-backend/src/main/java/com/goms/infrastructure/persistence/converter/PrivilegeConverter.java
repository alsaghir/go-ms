package com.goms.infrastructure.persistence.converter;


import com.goms.domain.model.privilege.Privilege;
import com.goms.infrastructure.persistence.entity.PrivilegeEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PrivilegeConverter {

  public Set<PrivilegeEntity> toPrivilegeEntitySet(Set<Privilege> privileges) {
    return privileges.parallelStream().map(this::toPrivilegeEntity).collect(Collectors.toSet());
  }

  private PrivilegeEntity toPrivilegeEntity(Privilege privilege) {
    return new PrivilegeEntity()
        .setId(privilege.privilegeConstant().getId())
        .setPrivilege(privilege.privilegeConstant());
  }

  public Set<Privilege> toDomainSet(Set<PrivilegeEntity> privilegeEntities) {
    return privilegeEntities
        .parallelStream()
        .map(privilegeEntity -> new Privilege(privilegeEntity.getPrivilege()))
        .collect(Collectors.toSet());
  }
  public Set<Privilege> toDomainSet(List<PrivilegeEntity> privilegeEntities) {
    return privilegeEntities
            .parallelStream()
            .map(privilegeEntity -> new Privilege(privilegeEntity.getPrivilege()))
            .collect(Collectors.toSet());
  }
}
