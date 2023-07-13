package com.crashcourse.SpringBootTraining.AccountData.dto;

import lombok.Data;

@Data
public class AccountResponseDtoForJwt {
    private String email;
    private String password;
    private String roles;
}
