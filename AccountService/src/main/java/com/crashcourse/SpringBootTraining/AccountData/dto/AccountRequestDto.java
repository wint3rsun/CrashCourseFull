package com.crashcourse.SpringBootTraining.AccountData.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountRequestDto {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, message = "name should have at least 2 characters")
    private String name;

    private String address;

    @NotEmpty
    @Email(message = "Email should be in proper format")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "mobNo should have at least 10 characters")
    private String mobNo;

    private String roles;

    private AuthRecordRequestDto authRecord;
}

