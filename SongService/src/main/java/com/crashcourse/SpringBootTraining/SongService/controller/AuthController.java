package com.crashcourse.SpringBootTraining.SongService.controller;

import com.crashcourse.SpringBootTraining.SongService.config.ProfileInfoUserDetailsService;
import com.crashcourse.SpringBootTraining.SongService.dto.AuthRequest;
import com.crashcourse.SpringBootTraining.SongService.entity.Profile;
import com.crashcourse.SpringBootTraining.SongService.service.JwtAuthService;
import com.crashcourse.SpringBootTraining.SongService.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "rest/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtAuthService jwtAuthService;

    @Autowired
    ProfileService profileService;

    @Autowired
    RestTemplate restTemplate;


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            // Check if profile service is available
            ResponseEntity<Profile> response = restTemplate.getForEntity(ProfileInfoUserDetailsService.PROFILEURL + "/" + authRequest.getUsername(), Profile.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                // Profile service is available, continue with authentication
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
                if (authentication.isAuthenticated()) {
                    return jwtAuthService.generateToken(authRequest.getUsername());
                } else {
                    throw new RuntimeException("Invalid user request!");
                }
            }
        } catch (Exception e) {
            // Profile service is down or encountered an error, call fallback method
            profileService.setFallbackUser();
            return jwtAuthService.generateToken(ProfileService.USERNAME);
        }

        // Fallback method in case profile service is down or encountered an error
        profileService.setFallbackUser();
        return jwtAuthService.generateToken(ProfileService.USERNAME);

    }

}
