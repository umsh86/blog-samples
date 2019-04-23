package com.eomdev.study01.domain.account;

import com.eomdev.study01.common.IntegrationTest;
import com.eomdev.study01.model.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends IntegrationTest {

  @Autowired
  private AccountRepository accountRepository;

  private Account saveAccount;
  private Email email;

  @BeforeEach
  public void setUp() throws Exception {
    String value = "user01@gamil.com";
    email = Email.of(value);

    AccountDto.CreateRequest createRequest = AccountDto.CreateRequest.builder()
        .email(email)
        .name("user01")
        .password("1234")
        .build();

    saveAccount = accountRepository.save(Account.create(createRequest));

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
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.query-accounts").exists())
            .andExpect(jsonPath("_links.update-account").exists())
            .andExpect(jsonPath("_links.profile").exists())
    ;
  }


  @DisplayName("Account 1건 조회. 존재하는 경우")
  @Test
  public void getAccount_success() throws Exception {

    mockMvc.perform(get("/accounts/{id}", saveAccount.getId()))
        .andDo(print())
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("password").doesNotExist())
        .andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.query-accounts").exists())
        .andExpect(jsonPath("_links.update-account").exists())
        .andExpect(jsonPath("_links.profile").exists())
    ;

  }

  @DisplayName("Account를 수정. 정상인 경우")
  @Test
  public void accountUpdate() throws Exception {

    // given -> modelMapper 사용으로 변경하는게 좋을려나?
    String updateName = "updateName";
    AccountDto.UpdateRequest updateRequest = AccountDto.UpdateRequest.builder()
        .id(saveAccount.getId())
        .name(updateName)
        .currentPassword(saveAccount.getPassword())
        .build();

    mockMvc.perform(put("/accounts/{id}", saveAccount.getId())
              .contentType(MediaType.APPLICATION_JSON_UTF8)
              .content(objectMapper.writeValueAsString(updateRequest)))
        .andDo(print())
        .andExpect(jsonPath("name").value(updateName))
        .andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.query-accounts").exists())
        .andExpect(jsonPath("_links.update-account").exists())
        .andExpect(jsonPath("_links.profile").exists())


    ;



  }

  @DisplayName("Account 1건 조회. 데이터가 존재하지 않는 경우")
  @Test
  public void getAccount_fail() throws Exception {

    mockMvc.perform(get("/accounts/{id}", "skdjfksdljf@asdf.com"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("status").value("400"))
        .andExpect(jsonPath("code").value("C003"))
    ;

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
