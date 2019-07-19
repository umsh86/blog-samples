package com.eomdev.study01.config;

import com.eomdev.study01.common.IntegrationTest;
import com.eomdev.study01.domain.account.Account;
import com.eomdev.study01.domain.account.AccountDto;
import com.eomdev.study01.domain.account.AccountService;
import com.eomdev.study01.model.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends IntegrationTest {



    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("인증 토큰(auth token)을 발급받는다")
    public void getAuthToken() throws Exception {
        // given
        String username = "user01@gamil.com";
        String password = "1234";
        AccountDto.CreateRequest createRequest = AccountDto.CreateRequest.builder()
                .email(Email.of(username))
                .name("user01")
                .password(password)
                .build();

        accountService.saveAccount(Account.create(createRequest));

        String clientId = "myApp";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")
                    .with(httpBasic(clientId, clientSecret))
                    .param("username", username)
                    .param("password", password)
                    .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

    }

}


