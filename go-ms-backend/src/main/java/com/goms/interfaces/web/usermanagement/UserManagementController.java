package com.goms.interfaces.web.usermanagement;

import com.goms.application.UserManagementService;
import com.goms.application.command.GenerateJwtTokenCommand;
import com.goms.application.data.JwtData;
import com.goms.application.data.assembler.JwtDataAssembler;
import com.goms.domain.shared.DomainException;
import com.goms.infrastructure.config.ConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@RepositoryRestController
@RequestMapping("/api")
public class UserManagementController {

  private final Logger logger = LogManager.getLogger(UserManagementController.class);
  private final UserManagementService userManagementService;
  private final ConfigProperties configProperties;
  private final PagedResourcesAssembler<?> pagedResourcesAssembler;

  @Autowired
  public UserManagementController(
      UserManagementService userManagementService,
      ConfigProperties configProperties,
      PagedResourcesAssembler<?> pagedResourcesAssembler) {
    this.userManagementService = userManagementService;
    this.configProperties = configProperties;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
  }

  @PostMapping(value = "/jwt")
  public ResponseEntity<?> jwt(@RequestBody GenerateJwtTokenCommand generateJwtTokenCommand)
      throws DomainException {

    JwtDataAssembler assembler = new JwtDataAssembler();
    JwtData responseModel = assembler.toModel(this.userManagementService.generateToken(
            generateJwtTokenCommand.getEmail(),
            generateJwtTokenCommand.getPassword(),
            generateJwtTokenCommand.isPasswordEncrypted())).add(
            WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(UserManagementController.class)
                            .jwt(new GenerateJwtTokenCommand("", "", false)))
                    .withSelfRel());


    return ResponseEntity.ok(responseModel);
  }

  @Bean
  public RepresentationModelProcessor<RepositoryLinksResource> jwtProcessor() {
    return new RepresentationModelProcessor<RepositoryLinksResource>() {
      @Override
      public RepositoryLinksResource process(RepositoryLinksResource model) {
        try {
          return model.add(
              WebMvcLinkBuilder.linkTo(
                      WebMvcLinkBuilder.methodOn(UserManagementController.class)
                          .jwt(new GenerateJwtTokenCommand("", "", false)))
                  .withRel("jwt"));
        } catch (DomainException ex) {
          logger.error("Error Processing Resource" + ex);
          return null;
        }
      }
    };
  }
}
