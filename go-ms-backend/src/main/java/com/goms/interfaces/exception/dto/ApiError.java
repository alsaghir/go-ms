package com.goms.interfaces.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {
  private HttpStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  private List<ApiSubError> apiSubErrors;

  public ApiError(HttpStatus status, List<ApiSubError> apiSubErrors) {
    this.status = status;
    this.timestamp = LocalDateTime.now();
    this.apiSubErrors = apiSubErrors;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public ApiError setStatus(HttpStatus status) {
    this.status = status;
    return this;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public ApiError setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public List<ApiSubError> getApiSubErrors() {
    return apiSubErrors;
  }

  public ApiError setApiSubErrors(List<ApiSubError> apiSubErrors) {
    this.apiSubErrors = apiSubErrors;
    return this;
  }
}
