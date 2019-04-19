package com.eomdev.study01.domain.account;

import com.eomdev.study01.model.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public class AccountDto {


  @Getter
  @ToString
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreateRequest {

    private Email email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Builder
    public CreateRequest(Email email, String name, String password) {
      this.email = email;
      this.name = name;
      this.password = password;
    }

  }




}
