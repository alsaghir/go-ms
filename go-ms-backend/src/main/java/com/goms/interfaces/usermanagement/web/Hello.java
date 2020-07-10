package com.goms.interfaces.usermanagement.web;

import com.goms.infrastructure.utility.APIController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@APIController
public class Hello {

  @PostMapping("/hello")
  public ResponseEntity<String> login() {
    return new ResponseEntity<String>("Hi", HttpStatus.OK);
  }
}
