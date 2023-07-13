package com.crashcourse.SpringBootTraining.AccountData;

import com.crashcourse.SpringBootTraining.AccountData.Entity.Account;
import com.crashcourse.SpringBootTraining.AccountData.Entity.AuthRecord;
import com.crashcourse.SpringBootTraining.AccountData.config.GenralConfig;
import com.crashcourse.SpringBootTraining.AccountData.dto.AuthRecordRequestDto;
import com.crashcourse.SpringBootTraining.AccountData.dto.AccountRequestDto;
import com.crashcourse.SpringBootTraining.AccountData.dto.AccountResponseDto;
import com.crashcourse.SpringBootTraining.AccountData.repository.AccountRepository;
import com.crashcourse.SpringBootTraining.AccountData.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {GenralConfig.class})
public class AccountServiceTests {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAccount() {
        // Preparing test data
        AccountRequestDto requestDto = new AccountRequestDto();
        requestDto.setName("John Doe");
        requestDto.setEmail("john.doe@example.com");
        AuthRecordRequestDto dto = new AuthRecordRequestDto();
        dto.setPassword("password");
        requestDto.setAuthRecord(dto);

        Account account = new Account();
        account.setId(1L);
        account.setName("John Doe");
        account.setEmail("john.doe@example.com");
        AuthRecord authRecord = new AuthRecord();
        authRecord.setPassword("password");
        account.setAuthRecord(authRecord);

        AccountResponseDto expectedResponse = new AccountResponseDto();
        expectedResponse.setId(1L);
        expectedResponse.setName("John Doe");
        expectedResponse.setEmail("john.doe@example.com");

        when(accountRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(requestDto, Account.class)).thenReturn(account);
        when(passwordEncoder.encode(requestDto.getAuthRecord().getPassword())).thenReturn("encodedPassword");
        when(accountRepository.save(account)).thenReturn(account);
        when(modelMapper.map(account, AccountResponseDto.class)).thenReturn(expectedResponse);

        // Performing the service method
        AccountResponseDto actualResponse = accountService.createAccount(requestDto);

        // Verifying the repository method is called with the correct argument
        verify(accountRepository, times(1)).findByEmail(requestDto.getEmail());
        verify(accountRepository, times(1)).save(account);

        // Verifying the modelMapper is called with the correct arguments
        verify(modelMapper, times(1)).map(requestDto, Account.class);
        verify(modelMapper, times(1)).map(account, AccountResponseDto.class);

        // Verifying the passwordEncoder is called with the correct argument
        verify(passwordEncoder, times(1)).encode(requestDto.getAuthRecord().getPassword());

        // Verifying the response
        assertThat(actualResponse, is(expectedResponse));
    }

    @Test
    public void testGetPerson() {
        // Preparing test data
        long id = 1L;
        AccountResponseDto responseDto = new AccountResponseDto();
        responseDto.setId(id);
        responseDto.setName("John Doe");
        responseDto.setEmail("john.doe@example.com");
        Account account = new Account(id,"John Doe","john.doe@example.com");

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(modelMapper.map(account, AccountResponseDto.class)).thenReturn(responseDto);

        // Performing the service method
        AccountResponseDto actualResponse = accountService.getPerson(id);

        // Verifying the repository method is called with the correct argument
        verify(accountRepository, times(1)).findById(id);
        verify(modelMapper, times(1)).map(account, AccountResponseDto.class);

        // Verifying the response
        assertThat(actualResponse, is(responseDto));
    }

    @Test
    public void testGetPersonNotFound() {
        // Preparing test data
        long id = 1L;

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        // Performing the service method and verify the exception
        assertThrows(RuntimeException.class, () -> accountService.getPerson(id));

        // Verifying the repository method is called with the correct argument
        verify(accountRepository, times(1)).findById(id);
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    public void testDeleteAccount_ExistingAccount() {
        // Preparing test data
        long id = 1L;
        Account account = new Account();
        account.setId(id);

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        // Performing the service method
        accountService.deleteAccount(id);

        // Verifying the repository method is called with the correct argument
        verify(accountRepository, times(1)).findById(id);
        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    public void testDeleteAccount_NonExistingAccount() {
        // Preparing test data
        long id = 1L;

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        // Performing the service method and verify the exception
        assertThrows(RuntimeException.class, () -> accountService.deleteAccount(id));

        // Verifying the repository method is called with the correct argument
        verify(accountRepository, times(1)).findById(id);
        verify(accountRepository, never()).delete(org.mockito.ArgumentMatchers.any(Account.class));

    }


    @Test
    public void testGetAccountResponseDtos() {
        // Preparing test data
        Account account1 = new Account();
        account1.setId(1L);
        account1.setName("John Doe");
        account1.setEmail("john.doe@example.com");

        Account account2 = new Account();
        account2.setId(2L);
        account2.setName("Jane Smith");
        account2.setEmail("jane.smith@example.com");

        List<Account> accounts = Arrays.asList(account1, account2);

        when(accountRepository.findAll()).thenReturn(accounts);

        AccountResponseDto responseDto1 = new AccountResponseDto();
        responseDto1.setId(1L);
        responseDto1.setName("John Doe");
        responseDto1.setEmail("john.doe@example.com");

        AccountResponseDto responseDto2 = new AccountResponseDto();
        responseDto2.setId(2L);
        responseDto2.setName("Jane Smith");
        responseDto2.setEmail("jane.smith@example.com");

        when(modelMapper.map(account1, AccountResponseDto.class)).thenReturn(responseDto1);
        when(modelMapper.map(account2, AccountResponseDto.class)).thenReturn(responseDto2);

        // Performing the service method
        List<AccountResponseDto> responseDtos = accountService.getAccountResponseDtos();

        // Verifying the repository method is called
        verify(accountRepository, times(1)).findAll();

        // Verifying the modelMapper is called
        verify(modelMapper, times(1)).map(account1, AccountResponseDto.class);
        verify(modelMapper, times(1)).map(account2, AccountResponseDto.class);

        // Verifying the response
        assertThat(responseDtos, hasSize(2));

        AccountResponseDto actualResponseDto1 = responseDtos.get(0);
        assertThat(actualResponseDto1.getId(), is(1L));
        assertThat(actualResponseDto1.getName(), is("John Doe"));
        assertThat(actualResponseDto1.getEmail(), is("john.doe@example.com"));

        AccountResponseDto actualResponseDto2 = responseDtos.get(1);
        assertThat(actualResponseDto2.getId(), is(2L));
        assertThat(actualResponseDto2.getName(), is("Jane Smith"));
        assertThat(actualResponseDto2.getEmail(), is("jane.smith@example.com"));
    }
}
