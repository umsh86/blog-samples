package com.eomdev.study01.common.exception;

import com.eomdev.study01.common.error.ErrorCode;
import com.eomdev.study01.common.exception.base.InvalidValueException;

public class PasswordInvalidException extends InvalidValueException {

  public PasswordInvalidException(String password) {
    super(password, ErrorCode.INVALID_TYPE_VALUE);
  }

}
