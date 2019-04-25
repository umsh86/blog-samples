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
import org.springframework.restdocs.hypermedia.LinkDescriptor;
import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AccountControllerTest extends IntegrationTest {

  @Autowired
  private AccountRepository accountRepository;

  private Account saveAccount;
  private Email email;

  private LinkDescriptor[] linkDescriptors = new LinkDescriptor[] {
      linkWithRel("self").description("link to self"),
      linkWithRel("profile").description("link to profile"),
      linkWithRel("query-accounts").description("link to query account"),
      linkWithRel("update-account").description("link to update account")
  };

  private FieldDescriptor[] requestDescriptors = new FieldDescriptor[]{
      fieldWithPath("name").description("Account name"),
      fieldWithPath("email.value").description("email address ex)umsh86@gmail.com"),
      fieldWithPath("email.id").description("not use"),
      fieldWithPath("email.host").description("not use"),
      fieldWithPath("password").description("Account password")
  };

  private FieldDescriptor[] responseDescriptors = new FieldDescriptor[] {
      fieldWithPath("id").description("Account UUID"),
      fieldWithPath("name").description("Account name"),
      fieldWithPath("email.value").description("email address ex)umsh86@gmail.com"),
      fieldWithPath("email.id").description("Account Email's id"),
      fieldWithPath("email.host").description("Account Email's host"),

      fieldWithPath("_links.self.href").description("link to self"),
      fieldWithPath("_links.profile.href").description("link to profile"),
      fieldWithPath("_links.query-accounts.href").description("link to query account"),
      fieldWithPath("_links.update-account.href").description("link to update account")
  };

  private FieldDescriptor[] errorResponseDescriptors = new FieldDescriptor[] {
      fieldWithPath("status").description("Account UUID"),
      fieldWithPath("code").description("이메일주소"),
      fieldWithPath("message").description("이메일주소"),
      fieldWithPath("errors").description("오류난 내용들"),
      fieldWithPath("errors[].field").description("오류난 필드명"),
      fieldWithPath("errors[].value").description("오류난 필드의 값"),
      fieldWithPath("errors[].reason").description("오류난 필드의 원인"),

  };



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
            .andDo(document("account-create",
                      links(linkDescriptors),
                      requestHeaders(
                          headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                          headerWithName(HttpHeaders.CONTENT_TYPE).description("content type")
                      ),
                      requestFields(requestDescriptors),
                      responseFields(responseDescriptors)

                ))
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
        .andDo(document("account-get",
            links(linkDescriptors),
            pathParameters(
                parameterWithName("id").description("account UUID")
            ),
            responseFields(responseDescriptors)

        ))
    ;

  }

  @DisplayName("Account를 수정. 정상인 경우")
  @Test
  public void accountUpdate_success() throws Exception {

    // given -> modelMapper 사용 고려해보기
    String updateName = "updateName";
    AccountDto.UpdateRequest updateRequest = AccountDto.UpdateRequest.builder()
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
        .andDo(document("account-update",
            links(linkDescriptors),
            pathParameters(
                parameterWithName("id").description("account UUID")
            ),
            requestFields(
                fieldWithPath("name").description("Account name"),
                fieldWithPath("currentPassword").description("Account current Account")
            ),
            responseFields(responseDescriptors)

        ))
    ;

  }

  @DisplayName("Account를 수정. 패스워드가 일치하지 않는 경우")
  @Test
  public void accountUpdate_fail() throws Exception {

    String updateName = "updateName";
    AccountDto.UpdateRequest updateRequest = AccountDto.UpdateRequest.builder()
        .name(updateName)
        .currentPassword("9999")
        .build();

    mockMvc.perform(put("/accounts/{id}", saveAccount.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectMapper.writeValueAsString(updateRequest)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("status").value(400))
        .andExpect(jsonPath("code").value("C005"));

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
        .andExpect(status().isBadRequest())
        .andDo(document("commmon-error",
            responseFields(errorResponseDescriptors)
        ))
    ;

  }

  @DisplayName("존재하는 계정 삭제. 성공")
  @Test
  public void accountDelete_success() throws Exception {
    // when, then
    mockMvc.perform(delete("/accounts/{id}", saveAccount.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.HAL_JSON_UTF8_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("account-delete",
            pathParameters(
                parameterWithName("id").description("account UUID")
            )
        ))
    ;

  }

  @DisplayName("존재하는 계정 삭제. 실패")
  @Test
  public void accountDelete_fail() throws Exception {

    // when, then
    mockMvc.perform(delete("/accounts/{id}", "1233456567")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.HAL_JSON_UTF8_VALUE))
        .andDo(print())
        .andExpect(status().isBadRequest());

  }





}
