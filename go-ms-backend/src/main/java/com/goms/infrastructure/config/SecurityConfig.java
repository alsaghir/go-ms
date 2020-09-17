package com.goms.infrastructure.config;

import com.goms.infrastructure.auth.UserDetailsLoadingService;
import com.goms.interfaces.web.usermanagement.JwtAuthenticationEntryPoint;
import com.goms.interfaces.web.usermanagement.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsLoadingService userDetailsLoadingService;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  public SecurityConfig(UserDetailsLoadingService userDetailsLoadingService,
      JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
    super();
    this.userDetailsLoadingService = userDetailsLoadingService;
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    // @formatter:on
    http.csrf()
        .disable()
        // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#cors
        .cors(Customizer.withDefaults())
        .authorizeRequests()
        .anyRequest()
        .permitAll()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  DaoAuthenticationProvider authenticationProvider() {
    // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#authentication-password-storage
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    return provider;
  }

  @Override
  protected UserDetailsService userDetailsService() {
    return this.userDetailsLoadingService;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#cors
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedMethod(HttpMethod.OPTIONS);
    configuration.addAllowedMethod(HttpMethod.PUT);
    configuration.addAllowedMethod(HttpMethod.DELETE);
    configuration.addAllowedMethod(HttpMethod.GET);
    configuration.addAllowedMethod(HttpMethod.POST);
    configuration.addAllowedHeader("access-control-allow-origin");
    configuration.addAllowedHeader("authorization");
    configuration.addAllowedHeader("content-type");
    configuration.setAllowCredentials(true);
    configuration.addAllowedOrigin(CorsConfiguration.ALL);
    System.out.println(configuration.getAllowedOrigins());
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  TokenFilter tokenFilter() {
    return new TokenFilter();
  }
}
