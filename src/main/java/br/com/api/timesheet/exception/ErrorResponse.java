package br.com.api.timesheet.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

  private final int statusCode;
  private final List<ApiError> errors;

  static ErrorResponse of(HttpStatus httpStatus, List<ApiError> errors) {
    return new ErrorResponse(httpStatus.value(), errors);
  }

  static ErrorResponse of(HttpStatus status, ApiError error) {
    return of(status, Collections.singletonList(error));
  }

  @JsonAutoDetect(fieldVisibility = ANY)
  @RequiredArgsConstructor
  public static class ApiError {

    private final String code;
    private final String message;

  }

}
