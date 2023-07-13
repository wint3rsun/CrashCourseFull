package com.crashcourse.SpringBootTraining.MovieService.service;

import com.crashcourse.SpringBootTraining.MovieService.entity.Account;
import com.crashcourse.SpringBootTraining.MovieService.util.UserUtil;
import com.crashcourse.SpringBootTraining.MovieService.config.AccountInfoUserDetailsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountService {

    public static final String ACCOUNTURL = "http://ACCOUNT-SERVICE/rest/account/getaccountbyusername/";

    public static final String USERNAME = "system@gmail.com";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AccountInfoUserDetailsService accountInfoUserDetailsService;


    @CircuitBreaker(name = "AccountInfoUserDetailsService", fallbackMethod = "fallbackGetAccount")
    public Account getAccount() {
        String username = UserUtil.getLoggedInUsername();
        Account account = restTemplate.getForObject(ACCOUNTURL + "/" + username, Account.class);
        return account;
    }

    public Account fallbackGetAccount(Exception exception) {
        return new Account("system@gmail.com", "system@123", "SYSTEM");
    }


    public void setFallbackUser() {
        UserDetails userDetails = accountInfoUserDetailsService.loadUserByUsername(USERNAME);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

}
