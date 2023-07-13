package com.crashcourse.SpringBootTraining.AccountData.service;


import com.crashcourse.SpringBootTraining.AccountData.Entity.Account;
import com.crashcourse.SpringBootTraining.AccountData.dto.AccountRequestDto;
import com.crashcourse.SpringBootTraining.AccountData.dto.AccountResponseDto;
import com.crashcourse.SpringBootTraining.AccountData.dto.AccountResponseDtoForJwt;
import com.crashcourse.SpringBootTraining.AccountData.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    List<AccountResponseDto> accountResponseDtos;

    @Value("${server.port}")
    private int port;



    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {

        Optional<Account> accountOptional = accountRepository.findByEmail(accountRequestDto.getEmail());
        if (accountOptional.isPresent()) {
            throw new RuntimeException("This username already exist please login");
        }
        Account account = modelMapper.map(accountRequestDto, Account.class);
        String encodedPassword = passwordEncoder.encode(accountRequestDto.getAuthRecord().getPassword());
        account.getAuthRecord().setPassword(encodedPassword);
        account.getAuthRecord().setUsername(account.getEmail());
        account.getAuthRecord().setPersonId(account);
        return modelMapper.map(accountRepository.save(account), AccountResponseDto.class);
    }

    public AccountResponseDto getPerson(long id) {
        return modelMapper.map(accountRepository.findById(id).get(), AccountResponseDto.class);
    }

    public List<AccountResponseDto> getAccountResponseDtos() {
        accountResponseDtos = accountRepository.findAll()
                .stream().map(account -> modelMapper.map(account, AccountResponseDto.class))
                .collect(Collectors.toList());
        return accountResponseDtos;
    }

    public void deleteAccount(long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account does not exist with this id");
        }
        accountRepository.delete(accountOptional.get());
    }

     public AccountResponseDtoForJwt getPersonByUsername(String username) {
        log.info("serving the request from port"+port);
        Optional<Account> accountOptional = accountRepository.findByEmail(username);
        AccountResponseDtoForJwt accountResponseDtoForJwt = modelMapper
                .map(accountOptional.get(), AccountResponseDtoForJwt.class);
        accountResponseDtoForJwt.setPassword(accountOptional.get().getAuthRecord().getPassword());
        return accountResponseDtoForJwt;
    }
}
