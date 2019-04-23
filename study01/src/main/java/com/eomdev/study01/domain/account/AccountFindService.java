package com.eomdev.study01.domain.account;

import com.eomdev.study01.common.exception.AccountNotFoundException;
import com.eomdev.study01.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AccountFindService {

  @Autowired
  private AccountRepository accountRepository;

  public Account findByEmail(Email email) {
    Optional<Account> byEmail = accountRepository.findByEmail(email);
    return byEmail.orElseThrow(() -> new AccountNotFoundException(email.getValue()));
  }

}
