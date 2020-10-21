package com.goms.infrastructure.utility;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@RepositoryRestController
@RequestMapping(value = "${spring.data.rest.base-path}")
public @interface APIController {
}
