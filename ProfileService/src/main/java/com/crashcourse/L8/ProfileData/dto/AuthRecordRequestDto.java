package com.crashcourse.L8.ProfileData.dto;

import jakarta.validation.constraints.NotEmpty;

public class AuthRecordRequestDto {
    @NotEmpty
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
