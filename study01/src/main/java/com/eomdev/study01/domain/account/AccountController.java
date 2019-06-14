package com.eomdev.study01.domain.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @Autowired
  private AccountService accountService;

  @PostMapping
  public ResponseEntity create(@RequestBody @Valid AccountDto.CreateRequest createRequest) {
    Account account = Account.create(createRequest);
    accountService.saveAccount(account);

    ControllerLinkBuilder selfLinkBuilder = linkTo(AccountController.class).slash(account.getId());
    AccountDto.AccountResource accountResource = new AccountDto.AccountResource(new AccountDto.AccountResponse(account.getId(), account.getEmail(), account.getName()));
    accountResource.add(new Link("/docs/index.html#account-create").withRel("profile"));

    URI createdUri = selfLinkBuilder.toUri();
    return ResponseEntity.created(createdUri).body(accountResource);
  }

  @GetMapping("/{id}")
  public ResponseEntity search(@PathVariable String id) {
    Account account = accountHelperService.findById(id);

    AccountDto.AccountResource accountResource = new AccountDto.AccountResource(new AccountDto.AccountResponse(account.getId(), account.getEmail(), account.getName()));
    accountResource.add(new Link("/docs/index.html#account-search").withRel("profile"));
    return ResponseEntity.ok(accountResource);
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@PathVariable String id, @RequestBody @Valid AccountDto.UpdateRequest updateRequest) {
    Account account = accountHelperService.findById(id);
    account.updateProfile(updateRequest);
    accountService.saveAccount(account);

    AccountDto.AccountResource accountResource = new AccountDto.AccountResource(new AccountDto.AccountResponse(account.getId(), account.getEmail(), account.getName()));
    accountResource.add(new Link("/docs/index.html#account-update").withRel("profile"));
    return ResponseEntity.ok(accountResource);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable String id) {
    Account account = accountHelperService.findById(id);
    accountRepository.delete(account);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity queryAccount(Pageable pageable,
                                     PagedResourcesAssembler<Account> assembler,
                                     Account account) {
    Page<Account> page = this.accountRepository.findAll(pageable);
    PagedResources<ResourceSupport> pagedResources = assembler.toResource(page, e -> new AccountDto.AccountResource(new AccountDto.AccountResponse(e.getId(), e.getEmail(), e.getName())));
    pagedResources.add(new Link("/docs/index.html#query-accounts").withRel("profile"));
    if (account != null) {
      pagedResources.add(linkTo(AccountController.class).withRel("account-create"));
    }
    return ResponseEntity.ok(pagedResources);
  }


}