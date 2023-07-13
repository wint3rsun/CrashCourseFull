package com.crashcourse.SpringBootTraining.MovieService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String email;
    private String password;
    private String roles;


}
