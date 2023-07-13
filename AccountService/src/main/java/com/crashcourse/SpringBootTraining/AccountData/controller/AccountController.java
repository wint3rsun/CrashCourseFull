package com.crashcourse.SpringBootTraining.AccountData.controller;

import com.crashcourse.SpringBootTraining.AccountData.dto.AccountRequestDto;
import com.crashcourse.SpringBootTraining.AccountData.dto.AccountResponseDto;
import com.crashcourse.SpringBootTraining.AccountData.dto.AccountResponseDtoForJwt;
import com.crashcourse.SpringBootTraining.AccountData.service.AccountService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/account")
public class AccountController {
    @Autowired
    AccountService accountService;


    @PostMapping
    private ResponseEntity<AccountResponseDto> createAccount(@Parameter(name = "PersonRequestDto",required = true) @RequestBody @Valid AccountRequestDto accountRequestDto) {

        return new ResponseEntity<>(accountService.createAccount(accountRequestDto), HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    private ResponseEntity<AccountResponseDto> getAccount(@PathVariable(value = "id") long id){
        return new ResponseEntity<AccountResponseDto>(accountService.getPerson(id),HttpStatus.OK);
    }

    @GetMapping(value = "getaccountbyusername/{username}")
    private ResponseEntity<AccountResponseDtoForJwt> getAccountByUsername(@PathVariable(value = "username") String username){
        return new ResponseEntity<AccountResponseDtoForJwt>(accountService.getPersonByUsername(username),HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    private ResponseEntity<String> deleteAccount(@PathVariable(value = "id") long id){
        accountService.deleteAccount(id);

        return new ResponseEntity<>("Account deleted successfully",HttpStatus.OK);

    }

    @GetMapping(value = "/list")
    private ResponseEntity<List<AccountResponseDto>> getAccounts(){
        return new ResponseEntity<>(accountService.getAccountResponseDtos(),HttpStatus.OK);

    }
}
