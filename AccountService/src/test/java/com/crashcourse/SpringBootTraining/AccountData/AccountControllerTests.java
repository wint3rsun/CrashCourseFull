package com.crashcourse.SpringBootTraining.AccountData;

import com.crashcourse.SpringBootTraining.AccountData.controller.AccountController;
import com.crashcourse.SpringBootTraining.AccountData.dto.AccountRequestDto;
import com.crashcourse.SpringBootTraining.AccountData.dto.AccountResponseDto;
import com.crashcourse.SpringBootTraining.AccountData.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private AccountController accountController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testCreateAccount() throws Exception {
        System.out.println("TEST: Test Create Account - Starting!");
        // Prepare test data
        AccountRequestDto requestDto = new AccountRequestDto();
        requestDto.setName("John Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setMobNo("1234567890");

        AccountResponseDto responseDto = new AccountResponseDto();
        responseDto.setId(1L);
        responseDto.setName("John Doe");
        responseDto.setEmail("john.doe@example.com");

        when(accountService.createAccount(any(AccountRequestDto.class))).thenReturn(responseDto);

        // Perform the request
        mockMvc.perform(post("/rest/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));

        // Verify the service method is called with the correct argument
        verify(accountService, times(1)).createAccount(any(AccountRequestDto.class));
        System.out.println("TEST: Test Create Account - Complete!");
    }

    // Utility method to convert object to JSON string
    private String asJsonString(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    @Test
    public void testGetAccount() throws Exception {
        System.out.println("TEST: Test Get Account - Starting!");
        // Prepare test data
        long id = 1L;
        AccountResponseDto responseDto = new AccountResponseDto();
        responseDto.setId(id);
        responseDto.setName("John Doe");
        responseDto.setEmail("john.doe@example.com");

        when(accountService.getPerson(id)).thenReturn(responseDto);

        // Perform the request
        mockMvc.perform(get("/rest/account/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));

        // Verify the service method is called with the correct argument
        verify(accountService, times(1)).getPerson(id);
        System.out.println("TEST: Test Get Account - Complete!");
    }

    @Test
    public void testDeleteAccount() throws Exception {
        System.out.println("TEST: Test Delete Account - Starting!");
        // Prepare test data
        long id = 1L;

        // Perform the request
        mockMvc.perform(delete("/rest/account/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Account deleted successfully"));

        // Verify the service method is called with the correct argument
        verify(accountService, times(1)).deleteAccount(id);
        System.out.println("TEST: Test Delete Account - Complete!");
    }

    @Test
    public void testGetAccounts() throws Exception {
        System.out.println("TEST: Test Get Accounts - Starting!");
        // Prepare test data
        List<AccountResponseDto> accounts = new ArrayList<>();
        AccountResponseDto account1 = new AccountResponseDto();
        account1.setId(1L);
        account1.setName("John Doe");
        account1.setEmail("john.doe@example.com");
        accounts.add(account1);
        // Add more accounts if needed

        when(accountService.getAccountResponseDtos()).thenReturn(accounts);

        // Perform the request
        mockMvc.perform(get("/rest/account/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(accounts.size())))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")));
        // Add more assertions for additional accounts if needed

        // Verify the service method is called
        verify(accountService, times(1)).getAccountResponseDtos();
        System.out.println("TEST: Test Get Accounts - Complete!");
    }
}
