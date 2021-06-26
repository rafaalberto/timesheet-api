package br.com.api.timesheet.exception;

import static br.com.api.timesheet.exception.ErrorResponse.ApiError;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  private static final String NO_MESSAGE_AVAILABLE = "No message available";

  private final MessageSource apiErrorMessageSource;

  public ApiExceptionHandler(MessageSource apiErrorMessageSource) {
    this.apiErrorMessageSource = apiErrorMessageSource;
  }

  /**
   * Handle exception.
   * @param exception - exception
   * @param locale - locale
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleNotValidException(
          MethodArgumentNotValidException exception, Locale locale) {
    Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();
    List<ApiError> apiErrors = errors
            .map(ObjectError::getDefaultMessage)
            .map(code -> toApiError(code, locale))
            .collect(Collectors.toList());

    ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  /**
   * Handle exception.
   * @param exception - exception
   * @param locale - locale
   * @return
   */
  @ExceptionHandler(InvalidFormatException.class)
  public ResponseEntity<ErrorResponse> handleInvalidFormatException(
          InvalidFormatException exception, Locale locale) {
    final String errorCode = "generic-1";
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    final ErrorResponse errorResponse = ErrorResponse.of(
            status, toApiError(errorCode, locale, exception.getValue()));
    return ResponseEntity.badRequest().body(errorResponse);
  }

  /**
   * Handle exception.
   * @param exception - exception
   * @param locale - locale
   * @return
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(
          BusinessException exception, Locale locale) {
    final HttpStatus status = exception.getHttpStatus();
    final ErrorResponse errorResponse = ErrorResponse.of(
            status, toApiError(exception.getCode(), locale));
    return ResponseEntity.badRequest().body(errorResponse);
  }

  private ApiError toApiError(String code, Locale locale, Object... args) {
    String message;
    try {
      message = apiErrorMessageSource.getMessage(code, args, locale);
    } catch (NoSuchMessageException e) {
      log.error("Could not find any message for {} code under {} locale", code, locale);
      message = NO_MESSAGE_AVAILABLE;
    }

    return new ApiError(code, message);
  }

}
