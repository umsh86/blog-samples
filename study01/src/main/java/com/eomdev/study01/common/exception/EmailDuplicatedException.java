package com.eomdev.study01.common.exception;

import com.eomdev.study01.common.error.ErrorCode;
import com.eomdev.study01.common.exception.base.InvalidValueException;

public class EmailDuplicatedException extends InvalidValueException {

  public EmailDuplicatedException(String email) {
    super(email, ErrorCode.EMAIL_DUPLICATION);
  }

}
