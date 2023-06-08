package com.crashcourse.SpringBootTraining.SongService.config;

import com.crashcourse.SpringBootTraining.SongService.entity.Profile;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ProfileInfoUserDetailsService implements UserDetailsService {

    public static final String PROFILEURL="http://PROFILE-SERVICE/rest/profile/getprofilebyusername/";

    @Autowired
    RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "ProfileInfoUserDetailsService", fallbackMethod = "fallbackloadUserByUsername")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Profile profile = restTemplate.getForObject(PROFILEURL+"/"+username, Profile.class);
       Optional<Profile> profileOptional =Optional.of(profile);
        return profileOptional.map(ProfileInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }

    public UserDetails fallbackloadUserByUsername(String username,Exception exception) throws UsernameNotFoundException{
        Profile profile= new Profile("system@gmail.com","system@123","SYSTEM");
        Optional<Profile> profileOptional =Optional.of(profile);
        return profileOptional.map(ProfileInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }

}
