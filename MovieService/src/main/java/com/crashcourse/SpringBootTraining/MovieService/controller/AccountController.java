package com.crashcourse.SpringBootTraining.MovieService.controller;

import com.crashcourse.SpringBootTraining.MovieService.entity.Account;
import com.crashcourse.SpringBootTraining.MovieService.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "rest/account")
public class AccountController {

    @Autowired
    AccountService accountService;


    @GetMapping(value = "/view")
    @Operation(summary = "View account", description = "View Account")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Account> getAccount() {
        return new ResponseEntity<>(accountService.getAccount(), HttpStatus.OK);
    }
}
