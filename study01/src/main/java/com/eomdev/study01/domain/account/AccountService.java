package com.eomdev.study01.domain.account;

import com.eomdev.study01.common.exception.AccountNotFoundException;
import com.eomdev.study01.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(Email.of(username))
                .orElseThrow(() -> new AccountNotFoundException(username));
        return new User(account.getEmail().getValue(), account.getPassword(), authorities(account.getRoles()));
    }

    private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                    .collect(Collectors.toSet());
    }

    public Account saveAccount(Account account) {
        account.encodePassword(passwordEncoder.encode(account.getPassword()));
        account.getRoles().add(AccountRole.USER);
        return accountRepository.save(account);
    }
}
