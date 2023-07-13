package com.crashcourse.SpringBootTraining.MovieService.controller;

import com.crashcourse.SpringBootTraining.MovieService.config.AccountInfoUserDetailsService;
import com.crashcourse.SpringBootTraining.MovieService.dto.AuthRequest;
import com.crashcourse.SpringBootTraining.MovieService.entity.Account;
import com.crashcourse.SpringBootTraining.MovieService.service.JwtAuthService;
import com.crashcourse.SpringBootTraining.MovieService.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "rest/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtAuthService jwtAuthService;

    @Autowired
    AccountService accountService;

    @Autowired
    RestTemplate restTemplate;


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            // Check if account service is available
            ResponseEntity<Account> response = restTemplate.getForEntity(AccountInfoUserDetailsService.ACCOUNTURL + "/" + authRequest.getUsername(), Account.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                // Account service is available, continue with authentication
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
                if (authentication.isAuthenticated()) {
                    return jwtAuthService.generateToken(authRequest.getUsername());
                } else {
                    throw new RuntimeException("Invalid user request!");
                }
            }
        } catch (Exception e) {
            // Account service is down or encountered an error, call fallback method
            accountService.setFallbackUser();
            return jwtAuthService.generateToken(AccountService.USERNAME);
        }

        // Fallback method in case account service is down or encountered an error
        accountService.setFallbackUser();
        return jwtAuthService.generateToken(AccountService.USERNAME);

    }

}
