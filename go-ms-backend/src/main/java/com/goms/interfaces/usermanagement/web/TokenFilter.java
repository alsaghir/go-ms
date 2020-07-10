package com.goms.interfaces.usermanagement.web;


import com.goms.application.shared.ApplicationException;
import com.goms.infrastructure.auth.MutableHttpServletRequest;
import com.goms.infrastructure.config.UserDetailsLoadingService;
import com.goms.interfaces.usermanagement.facade.UserManagementFacade;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Filters out of Spring Container magic, we have to get the bean manually for example
 *
 * <pre>{@code
 * if (this.whateverBean == null) {
 * WebApplicationContext ctx =
 * WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
 * if (ctx != null) whateverBean = ctx.getBean(WhateverBean.class);
 * }
 * }</pre>
 *
 * Or use @Component annotation
 */
public class TokenFilter extends OncePerRequestFilter {

  private UserManagementFacade userManagementFacade;
  private UserDetailsLoadingService userDetailsLoadingService;

  @Override
  protected void doFilterInternal(
          HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    // Dependencies
    if (userManagementFacade == null) {
      WebApplicationContext ctx =
          WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
      if (ctx != null) {
        userManagementFacade = ctx.getBean(UserManagementFacade.class);
        userDetailsLoadingService = ctx.getBean(UserDetailsLoadingService.class);
      }
    }

    // debugger provided by Spring
    final boolean debug = this.logger.isDebugEnabled();

    String authHeader =
        request.getHeader(AUTHORIZATION) == null ? null : request.getHeader(AUTHORIZATION).trim();

    MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);

    if (authHeader != null && StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {

      if (debug) this.logger.debug("Authorization header found");

      String token = authHeader.substring("Bearer ".length());

      Integer userId;
      try {
        userId = this.userManagementFacade.validateAndExtractUserIdFromToken(token);

        UserDetails userDetails = userDetailsLoadingService.loadUserByUserId(userId);
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (ApplicationException ex) {
        logger.error("Failure validating, parsing token or authenticating user", ex);
      }
    } else {
      logger.warn("No token provided !");
    }

    filterChain.doFilter(mutableRequest, response);
  }
}
