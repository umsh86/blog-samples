package com.eomdev.study01.common.error;


import lombok.Getter;

@Getter
public enum ErrorCode {

  // Common
  INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
  METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
  ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
  INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
  INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
  HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),


  // Account
  EMAIL_DUPLICATION(400, "A001", "Email is Duplication"),
  ;

  private final int status;
  private final String code;
  private final String message;

  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }

}
