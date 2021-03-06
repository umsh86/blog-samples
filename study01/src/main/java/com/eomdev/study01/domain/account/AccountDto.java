package com.eomdev.study01.domain.account;

import com.eomdev.study01.model.Email;
import lombok.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import javax.validation.constraints.NotBlank;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

  @Getter
  public static class AccountResource extends Resource<AccountResponse> {

    public AccountResource(AccountResponse content, Link... links) {
      super(content, links);
      ControllerLinkBuilder selfLinkBuilder = linkTo(AccountController.class).slash(content.getId());
      add(linkTo(AccountController.class).slash(content.getId()).withSelfRel());
      add(linkTo(AccountController.class).withRel("query-accounts"));
      add(selfLinkBuilder.withRel("update-account"));
    }

  }

  @Getter
  @ToString
  public static class AccountResponse {

    @NotBlank
    private String id;

    private Email email;

    @NotBlank
    private String name;

    @Builder
    public AccountResponse(String id, Email email, String name) {
      this.id = id;
      this.email = email;
      this.name = name;
    }
  }


  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class UpdateRequest {

    @NotBlank
    private String name;

    @Builder
    public UpdateRequest(@NotBlank String name) {
      this.name = name;
    }
  }




}
