package com.eomdev.study01.common.error;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  private int status;
  private String code;
  private String message;
  private List<FieldError> errors;

  public static ErrorResponse create(final ErrorCode errorCode) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.message = errorCode.getMessage();
    errorResponse.status = errorCode.getStatus();
    errorResponse.code = errorCode.getCode();
    errorResponse.errors = new ArrayList<>();
    return errorResponse;
  }

  public static ErrorResponse create(final ErrorCode code, final BindingResult bindingResult) {
    ErrorResponse errorResponse = create(code);
    errorResponse.errors = FieldError.create(bindingResult);
    return errorResponse;
  }

  public static ErrorResponse create(final ErrorCode code, final List<FieldError> errors) {
    ErrorResponse errorResponse = create(code);
    errorResponse.errors = errors;
    return errorResponse;
  }

  public static ErrorResponse create(MethodArgumentTypeMismatchException e) {
    final String value = e.getValue() == null ? "" : e.getValue().toString();
    final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.create(e.getName(), value, e.getErrorCode());
    return create(ErrorCode.INVALID_TYPE_VALUE, errors);
  }




  @Getter
  public static class FieldError {
    private String field;
    private String value;
    private String reason;

    public FieldError(final String field, final String value, final String reason) {
      this.field = field;
      this.value = value;
      this.reason = reason;
    }

    public static List<FieldError> create(final String field, final String value, final String reason) {
      List<FieldError> fieldErrors = new ArrayList<>();
      fieldErrors.add(new FieldError(field, value, reason));
      return fieldErrors;
    }

    private static List<FieldError> create(final BindingResult bindingResult) {
      final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
      return fieldErrors.stream()
          .map(error -> new FieldError(
              error.getField(),
              error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
              error.getDefaultMessage()))
          .collect(Collectors.toList());
    }

  }
}
