package com.goms.interfaces.usermanagement.web;

import com.goms.infrastructure.utility.APIController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

@APIController
public class Hello {

  @PostMapping("/hello")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<String> helloAuth() {
    return new ResponseEntity<String>("Hi", HttpStatus.OK);
  }

  @PostMapping("/hello2")
  public ResponseEntity<String> helloNotAuth() {
    return new ResponseEntity<String>("Hi", HttpStatus.OK);
  }

  @PostMapping("/hello3")
  @PreAuthorize("hasAnyAuthority('OP_CREATE_USER')")
  public ResponseEntity<String> helloRole() {
    return new ResponseEntity<String>("Hi", HttpStatus.OK);
  }

  @PostMapping("/hello4")
  @PreAuthorize("hasAuthority('OP_DELETE_ACCOUNT')")
  public ResponseEntity<String> helloRoleNoThere() {
    return new ResponseEntity<String>("Hi", HttpStatus.OK);
  }
}
