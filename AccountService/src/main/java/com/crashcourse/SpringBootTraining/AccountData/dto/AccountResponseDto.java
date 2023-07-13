package com.crashcourse.SpringBootTraining.AccountData.dto;

import lombok.Data;

@Data
public class AccountResponseDto {

    private Long id;

    private String name;

    private String address;

    private String email;

    private String mobNo;
}

