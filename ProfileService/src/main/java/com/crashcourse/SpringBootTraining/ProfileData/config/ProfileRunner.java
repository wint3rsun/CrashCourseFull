package com.crashcourse.SpringBootTraining.ProfileData.config;

import com.crashcourse.SpringBootTraining.ProfileData.Entity.AuthRecord;
import com.crashcourse.SpringBootTraining.ProfileData.Entity.Profile;
import com.crashcourse.SpringBootTraining.ProfileData.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ProfileRunner implements ApplicationRunner {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Profile> profile = profileRepository.findById(1L);
        if (profile.isEmpty()) {
            log.info("creating system profile");
            AuthRecord authRecord = new AuthRecord();
            authRecord.setUsername("system@gmail.com");
            authRecord.setPassword(passwordEncoder.encode("system@123"));
            Profile systemProfile = new Profile(1L, "system", "system", "system@gmail.com", "9678123456", "SYSTEM", authRecord);
            authRecord.setPersonId(systemProfile);
            profileRepository.save(systemProfile);


        }


    }
}
