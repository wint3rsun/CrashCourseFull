package com.crashcourse.SpringBootTraining.SongService.service;

import com.crashcourse.SpringBootTraining.SongService.util.UserUtil;
import com.crashcourse.SpringBootTraining.SongService.config.ProfileInfoUserDetailsService;
import com.crashcourse.SpringBootTraining.SongService.entity.Profile;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProfileService {

    public static final String PROFILEURL="http://PROFILE-SERVICE/rest/profile/getprofilebyusername/";

    public static final String USERNAME="system@gmail.com";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProfileInfoUserDetailsService profileInfoUserDetailsService;


    @CircuitBreaker(name = "ProfileInfoUserDetailsService", fallbackMethod = "fallbackGetProfile")
    public Profile getProfile(){
        String username = UserUtil.getLoggedInUsername();
        Profile profile = restTemplate.getForObject(PROFILEURL+"/"+username, Profile.class);
        return profile;
    }

    public Profile fallbackGetProfile(Exception exception){
        return new Profile("system@gmail.com","system@123","SYSTEM");
    }


    public void setFallbackUser(){
        UserDetails userDetails =profileInfoUserDetailsService.loadUserByUsername(USERNAME);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

}
