package com.crashcourse.SpringBootTraining.AccountData.config;


import com.crashcourse.SpringBootTraining.AccountData.dto.AccountResponseDto;
import io.swagger.v3.oas.models.OpenAPI;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GenralConfig {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public List<AccountResponseDto> getList(){
        return new ArrayList<AccountResponseDto>();
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
