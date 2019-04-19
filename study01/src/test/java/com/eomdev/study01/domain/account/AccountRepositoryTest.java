package com.eomdev.study01.domain.account;

import com.eomdev.study01.common.RepositoryTest;
import com.eomdev.study01.model.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountRepositoryTest extends RepositoryTest {

  @Autowired
  private AccountRepository accountRepository;

  private Account saveAccount;
  private Email email;

  @BeforeEach
  public void setUp() throws Exception {
    String value = "eomdev@gmail.com";
    email = Email.of(value);

    AccountDto.CreateRequest createRequest = AccountDto.CreateRequest.builder()
        .email(email)
        .name("eomdev")
        .password("1234")
        .build();

    saveAccount = accountRepository.save(Account.create(createRequest));

  }

  @DisplayName("새로 생성된 계정이 정상적으로 존재하는지 테스트")
  @Test
  public void findByEmailTest() {

    // when
    Optional<Account> byEmail = accountRepository.findByEmail(email);
    Account account = byEmail.get();

    // then
    assertThat(account.getEmail().getValue()).isEqualTo(email.getValue());

  }

  @DisplayName("회원가입시 기존 가입자 중에서 이메일이 중복되는 경우")
  @Test
  public void existsByEmail_true() {
    boolean existsByEmail = accountRepository.existsByEmail(email);
    assertThat(existsByEmail).isTrue();
  }

  @DisplayName("회원가입시 기존 가입자 중에서 이메일이 중복되지 않는 경우")
  @Test
  public void existsByEmail_false() {
    boolean existsByEmail = accountRepository.existsByEmail(Email.of("sdkafljasdfklja@sdkljf.com"));
    assertThat(existsByEmail).isFalse();
  }

}
