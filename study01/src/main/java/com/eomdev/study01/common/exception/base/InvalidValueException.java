package com.eomdev.study01.common.exception.base;

import com.eomdev.study01.common.error.ErrorCode;

public class InvalidValueException extends BusinessException {

  public InvalidValueException(ErrorCode errorCode) {
    super(errorCode);
  }

  public InvalidValueException(String value, ErrorCode errorCode) {
    super(value, errorCode);
  }

}