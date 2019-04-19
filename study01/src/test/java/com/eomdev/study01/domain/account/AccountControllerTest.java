package com.eomdev.study01.domain.account;

import com.eomdev.study01.common.IntegrationTest;
import com.eomdev.study01.model.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends IntegrationTest {

  @BeforeEach
  public void setUp() {

  }


  @DisplayName("Account를 생성하고, 201 응답을 받는다")
  @Test
  public void creatAccount() throws Exception {

    // given
    AccountDto.CreateRequest createRequest = AccountDto.CreateRequest.builder()
        .email(Email.of("eomdev@gmail.com"))
        .name("eom")
        .password("1234")
        .build();

    // when, then
    mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").exists())
            .andExpect(header().exists(HttpHeaders.LOCATION))
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));
  }

  @DisplayName("Account를 생성하는데, 필수 파라미터가 넘어오지 않는 경우")
  @Test
  public void createAccount_Bad_Request() throws Exception {
    // given
    AccountDto.CreateRequest createRequest = AccountDto.CreateRequest.builder()
        .email(Email.of("eomdev@gmail.com"))
        .password("1234")
        .build();

    // when, then
    mockMvc.perform(post("/accounts")
              .contentType(MediaType.APPLICATION_JSON_UTF8)
              .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
              .content(objectMapper.writeValueAsString(createRequest)))
        .andDo(print())
        .andExpect(status().isBadRequest());

  }

  // CRUD

  // 회원가입
  // 회원조회(리스트, 한건)
  // 회원삭제?

}
