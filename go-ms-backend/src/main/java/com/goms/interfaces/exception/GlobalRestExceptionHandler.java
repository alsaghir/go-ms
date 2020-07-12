package com.goms.interfaces.exception;

import com.goms.application.shared.ApplicationException;
import com.goms.domain.shared.DomainError;
import com.goms.interfaces.exception.dto.ApiError;
import com.goms.interfaces.exception.dto.ApiSubError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public final class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

  @ExceptionHandler(value = ApplicationException.class)
  protected ResponseEntity<Object> handleApplicationException(
      ApplicationException applicationException,
      HandlerMethod handlerMethod,
      WebRequest webRequest,
      HttpServletRequest servletRequest,
      HttpServletResponse servletResponse,
      HttpMethod httpMethod) {

    logger.error(applicationException.getMessage(), applicationException);

    List<ApiSubError> apiSubErrors =
        applicationException.domainErrors().stream()
            .map(
                domainError ->
                    new ApiSubError(
                        String.format("e%05d", domainError.getErrorCode()),
                        domainError.getErrorMessage()))
            .collect(Collectors.toList());

    if (applicationException.domainErrors().contains(DomainError.BAD_CREDENTIALS))
      return handleExceptionInternal(
          applicationException,
          new ApiError(HttpStatus.UNAUTHORIZED, apiSubErrors),
          new HttpHeaders(),
          HttpStatus.UNAUTHORIZED,
          webRequest);
    else
      return handleExceptionInternal(
          applicationException,
          new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, apiSubErrors),
          new HttpHeaders(),
          HttpStatus.INTERNAL_SERVER_ERROR,
          webRequest);
  }

  @ExceptionHandler(value = Exception.class)
  protected ResponseEntity<Object> handleAnyException(
      Exception ex,
      HandlerMethod handlerMethod,
      WebRequest webRequest,
      HttpServletRequest servletRequest,
      HttpServletResponse servletResponse,
      HttpMethod httpMethod) {

    logger.error(ex.getMessage(), ex);

    List<ApiSubError> apiSubErrors =
        Collections.singletonList(
            new ApiSubError(
                String.format("e%05d", DomainError.UNEXPECTED_ERROR.getErrorCode()),
                DomainError.UNEXPECTED_ERROR.getErrorMessage()));

    if (ex instanceof AccessDeniedException) {
      return handleExceptionInternal(
          ex,
          new ApiError(HttpStatus.UNAUTHORIZED, apiSubErrors),
          new HttpHeaders(),
          HttpStatus.UNAUTHORIZED,
          webRequest);

    } else {
      return handleExceptionInternal(
          ex,
          new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, apiSubErrors),
          new HttpHeaders(),
          HttpStatus.INTERNAL_SERVER_ERROR,
          webRequest);
    }
  }
}
