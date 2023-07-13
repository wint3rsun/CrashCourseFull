package com.crashcourse.SpringBootTraining.AccountData.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRecordRequestDto {
    @NotEmpty
    private String password;
}

