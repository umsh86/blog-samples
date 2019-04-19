package com.eomdev.study01.common.exception.base;

import com.eomdev.study01.common.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

  public EntityNotFoundException(String message) {
    super(message, ErrorCode.ENTITY_NOT_FOUND);
  }

}
