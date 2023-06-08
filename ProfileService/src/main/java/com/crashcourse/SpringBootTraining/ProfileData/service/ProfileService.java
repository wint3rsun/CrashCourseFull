package com.crashcourse.SpringBootTraining.ProfileData.service;


import com.crashcourse.SpringBootTraining.ProfileData.dto.ProfileRequestDto;
import com.crashcourse.SpringBootTraining.ProfileData.dto.ProfileResponseDto;
import com.crashcourse.SpringBootTraining.ProfileData.dto.ProfileResponseDtoForJwt;
import com.crashcourse.SpringBootTraining.ProfileData.Entity.Profile;
import com.crashcourse.SpringBootTraining.ProfileData.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    List<ProfileResponseDto> profileResponseDtos;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${server.port}")
    private int port;



    public ProfileResponseDto createProfile(ProfileRequestDto profileRequestDto) {

        Optional<Profile> profileOptional = profileRepository.findByEmail(profileRequestDto.getEmail());
        if (profileOptional.isPresent()) {
            throw new RuntimeException("This username already exist please login");
        }
        Profile profile = modelMapper.map(profileRequestDto, Profile.class);
        String encodedPassword = passwordEncoder.encode(profileRequestDto.getAuthRecord().getPassword());
        profile.getAuthRecord().setPassword(encodedPassword);
        profile.getAuthRecord().setUsername(profile.getEmail());
        profile.getAuthRecord().setPersonId(profile);
        return modelMapper.map(profileRepository.save(profile), ProfileResponseDto.class);
    }

    public ProfileResponseDto getPerson(long id) {
        return modelMapper.map(profileRepository.findById(id).get(), ProfileResponseDto.class);
    }

    public List<ProfileResponseDto> getProfileResponseDtos() {
        profileResponseDtos = profileRepository.findAll().stream().map(profile -> modelMapper.map(profile, ProfileResponseDto.class)).collect(Collectors.toList());
        return profileResponseDtos;
    }

    public void deleteProfile(long id) {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        if (profileOptional.isEmpty()) {
            throw new RuntimeException("Profile does not exist with this id");
        }
        profileRepository.delete(profileOptional.get());
    }

     public ProfileResponseDtoForJwt getPersonByUsername(String username) {

        log.info("serving the request from port"+port);

        Optional<Profile> profileOptional = profileRepository.findByEmail(username);

        ProfileResponseDtoForJwt profileResponseDtoForJwt=modelMapper.map(profileOptional.get(),ProfileResponseDtoForJwt.class);

        profileResponseDtoForJwt.setPassword(profileOptional.get().getAuthRecord().getPassword());

        return profileResponseDtoForJwt;

    }
}
