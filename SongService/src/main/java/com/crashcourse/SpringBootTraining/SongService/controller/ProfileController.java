package com.crashcourse.SpringBootTraining.SongService.controller;

import com.crashcourse.SpringBootTraining.SongService.entity.Profile;
import com.crashcourse.SpringBootTraining.SongService.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "rest/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;


    @GetMapping(value = "/view")
    @Operation(summary = "View profile", description = "View Profile")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Profile> getProfile(){
        return new ResponseEntity<>(profileService.getProfile(), HttpStatus.OK);
    }
}
