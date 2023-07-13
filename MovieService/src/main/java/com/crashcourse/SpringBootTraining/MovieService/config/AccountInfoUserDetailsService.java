package com.crashcourse.SpringBootTraining.MovieService.config;

import com.crashcourse.SpringBootTraining.MovieService.entity.Account;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class AccountInfoUserDetailsService implements UserDetailsService {

    public static final String ACCOUNTURL = "http://ACCOUNT-SERVICE/rest/account/getaccountbyusername/";

    @Autowired
    RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "AccountInfoUserDetailsService", fallbackMethod = "fallbackloadUserByUsername")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = restTemplate.getForObject(ACCOUNTURL + "/" + username, Account.class);
        Optional<Account> accountOptional = Optional.of(account);
        return accountOptional.map(AccountInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }

    public UserDetails fallbackloadUserByUsername(String username, Exception exception) throws UsernameNotFoundException {
        Account account = new Account("system@gmail.com", "system@123", "SYSTEM");
        Optional<Account> accountOptional = Optional.of(account);
        return accountOptional.map(AccountInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }

}
