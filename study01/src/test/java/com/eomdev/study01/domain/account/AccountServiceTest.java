package com.eomdev.study01.domain.account;

import com.eomdev.study01.common.exception.AccountNotFoundException;
import com.eomdev.study01.model.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest  {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @DisplayName("Spring Security를 통한 loadUserByUsername 성공 테스트")
    @Test
    public void findByUsername() {

        String username = "user01@gmail.com";
        String password = "111111";
        AccountDto.CreateRequest createRequest = AccountDto.CreateRequest.builder()
                .email(Email.of(username))
                .name("user01")
                .password(password)
                .build();

        accountService.saveAccount(Account.create(createRequest));

        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();

    }

    @DisplayName("Spring Security를 통한 loadUserByUsername 실패 테스트")
    @Test
    public void findByUsername_fail() {
        String username = "invalid@gmail.com";

        assertThrows(AccountNotFoundException.class, () -> {
            accountService.loadUserByUsername(username);
        });
    }

}
