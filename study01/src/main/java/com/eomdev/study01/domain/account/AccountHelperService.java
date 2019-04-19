package com.eomdev.study01.domain.account;

import com.eomdev.study01.common.exception.base.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AccountHelperService {

  private AccountRepository accountRepository;

  public AccountHelperService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account findById(String id) {
    Optional<Account> member = accountRepository.findById(id);
    member.orElseThrow(() -> new EntityNotFoundException(id));
    return member.get();
  }

}
