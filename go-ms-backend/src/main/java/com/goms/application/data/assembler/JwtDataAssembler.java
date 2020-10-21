package com.goms.application.data.assembler;

import com.goms.application.data.JwtData;
import com.goms.interfaces.web.usermanagement.UserManagementController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class JwtDataAssembler extends RepresentationModelAssemblerSupport<String, JwtData> {

  public JwtDataAssembler() {
    super(UserManagementController.class, JwtData.class);
  }

  @Override
  public JwtData toModel(String entity) {
    return new JwtData().setToken(entity);
  }
}
