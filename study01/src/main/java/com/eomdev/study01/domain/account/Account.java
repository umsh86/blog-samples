package com.eomdev.study01.domain.account;

import com.eomdev.study01.model.Email;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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


//  public static Account create(Email email, String name, String password) {
//    Account account = new Account();
//    account.email = email;
//    account.name = name;
//    account.password = password;
//    return account;
//  }



  public static Account create(AccountDto.CreateRequest request) {
    Account account = new Account();
    account.email = request.getEmail();
    account.name = request.getName();
    account.password = request.getPassword();
    return account;
  }
//
//  public void updateProfile(AccountDto.UpdateRequest updateRequest) {
//    this.name = updateRequest.getName();
//    this.password = updateRequest.getPassword();
//  }


}
