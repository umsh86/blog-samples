package com.eomdev.study01.common.exception;

import com.eomdev.study01.common.exception.base.EntityNotFoundException;

public class AccountNotFoundException extends EntityNotFoundException {

  public AccountNotFoundException(String accountId) {
    super(accountId + " is not found");
  }

}