package com.eomdev.study01.domain.account;

import com.eomdev.study01.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
  Optional<Account> findByEmail(Email email);
  boolean existsByEmail(Email email);
}
