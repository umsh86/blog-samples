package com.eomdev.study01.domain.account;

import com.eomdev.study01.model.Email;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "mt_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false, of = { "id" })
@Getter
@ToString(of = {"email", "name"})
public class Account {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(length = 100)
  private String id;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true, updatable = false, length = 50))
  private Email email;

  @Column(length = 100)
  private String name;

  @Column(length = 100)
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<AccountRole> roles = new HashSet<>();


  public static Account create(AccountDto.CreateRequest request) {
    Account account = new Account();
    account.email = request.getEmail();
    account.name = request.getName();
    account.password = request.getPassword();
    return account;
  }

  public void updateProfile(AccountDto.UpdateRequest updateRequest) {
    this.name = updateRequest.getName();
  }

  private boolean checkPassword(String password) {
    return this.password.equals(password);
  }

  public void encodePassword(String encodePassword) {
    this.password = encodePassword;
  }




}
