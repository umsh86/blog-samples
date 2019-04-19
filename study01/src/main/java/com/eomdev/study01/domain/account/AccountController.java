package com.eomdev.study01.domain.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController("/accounts")
public class AccountController {

  @Autowired
  private AccountRepository accountRepository;

  @PostMapping
  public ResponseEntity create(@RequestBody @Valid AccountDto.CreateRequest createRequest) {
    Account account = Account.create(createRequest);
    accountRepository.save(account);

    URI createdUri = linkTo(AccountController.class).slash(account.getId()).toUri();
    return ResponseEntity.created(createdUri).body(account);
  }

  @GetMapping("/{id}")
  public ResponseEntity search(@PathVariable String id) {

    return ResponseEntity.ok().build();
  }


}
