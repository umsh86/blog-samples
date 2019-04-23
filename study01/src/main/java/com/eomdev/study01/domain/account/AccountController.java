package com.eomdev.study01.domain.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private AccountHelperService accountHelperService;

  @PostMapping
  public ResponseEntity create(@RequestBody @Valid AccountDto.CreateRequest createRequest) {
    Account account = Account.create(createRequest);
    accountRepository.save(account);

    ControllerLinkBuilder selfLinkBuilder = linkTo(AccountController.class).slash(account.getId());
    AccountDto.AccountResource accountResource = new AccountDto.AccountResource(new AccountDto.CreateResponse(account.getId(), account.getEmail(), account.getName()));

    URI createdUri = selfLinkBuilder.toUri();
    return ResponseEntity.created(createdUri).body(accountResource);
  }

  @GetMapping("/{id}")
  public ResponseEntity search(@PathVariable String id) {
    Account account = accountHelperService.findById(id);
    AccountDto.AccountResource accountResource = new AccountDto.AccountResource(new AccountDto.CreateResponse(account.getId(), account.getEmail(), account.getName()));
    return ResponseEntity.ok(accountResource);
  }



}
