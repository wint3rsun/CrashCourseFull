package com.crashcourse.L8.ProfileData;

import com.crashcourse.L8.ProfileData.Entity.AuthRecord;
import com.crashcourse.L8.ProfileData.Entity.Profile;
import com.crashcourse.L8.ProfileData.config.GenralConfig;
import com.crashcourse.L8.ProfileData.dto.AuthRecordRequestDto;
import com.crashcourse.L8.ProfileData.dto.ProfileRequestDto;
import com.crashcourse.L8.ProfileData.dto.ProfileResponseDto;
import com.crashcourse.L8.ProfileData.repository.ProfileRepository;
import com.crashcourse.L8.ProfileData.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {GenralConfig.class})
public class ProfileServiceTests {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateProfile() {
        // Preparing test data
        ProfileRequestDto requestDto = new ProfileRequestDto();
        requestDto.setName("John Doe");
        requestDto.setEmail("john.doe@example.com");
        AuthRecordRequestDto dto = new AuthRecordRequestDto();
        dto.setPassword("password");
        requestDto.setAuthRecord(dto);

        Profile profile = new Profile();
        profile.setId(1L);
        profile.setName("John Doe");
        profile.setEmail("john.doe@example.com");
        AuthRecord authRecord = new AuthRecord();
        authRecord.setPassword("password");
        profile.setAuthRecord(authRecord);

        ProfileResponseDto expectedResponse = new ProfileResponseDto();
        expectedResponse.setId(1L);
        expectedResponse.setName("John Doe");
        expectedResponse.setEmail("john.doe@example.com");

        when(profileRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(requestDto, Profile.class)).thenReturn(profile);
        when(passwordEncoder.encode(requestDto.getAuthRecord().getPassword())).thenReturn("encodedPassword");
        when(profileRepository.save(profile)).thenReturn(profile);
        when(modelMapper.map(profile, ProfileResponseDto.class)).thenReturn(expectedResponse);

        // Performing the service method
        ProfileResponseDto actualResponse = profileService.createProfile(requestDto);

        // Verifying the repository method is called with the correct argument
        verify(profileRepository, times(1)).findByEmail(requestDto.getEmail());
        verify(profileRepository, times(1)).save(profile);

        // Verifying the modelMapper is called with the correct arguments
        verify(modelMapper, times(1)).map(requestDto, Profile.class);
        verify(modelMapper, times(1)).map(profile, ProfileResponseDto.class);

        // Verifying the passwordEncoder is called with the correct argument
        verify(passwordEncoder, times(1)).encode(requestDto.getAuthRecord().getPassword());

        // Verifying the response
        assertThat(actualResponse, is(expectedResponse));
    }

    @Test
    public void testGetPerson() {
        // Preparing test data
        long id = 1L;
        ProfileResponseDto responseDto = new ProfileResponseDto();
        responseDto.setId(id);
        responseDto.setName("John Doe");
        responseDto.setEmail("john.doe@example.com");
        Profile profile = new Profile(id,"John Doe","john.doe@example.com");

        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));
        when(modelMapper.map(profile, ProfileResponseDto.class)).thenReturn(responseDto);

        // Performing the service method
        ProfileResponseDto actualResponse = profileService.getPerson(id);

        // Verifying the repository method is called with the correct argument
        verify(profileRepository, times(1)).findById(id);
        verify(modelMapper, times(1)).map(profile, ProfileResponseDto.class);

        // Verifying the response
        assertThat(actualResponse, is(responseDto));
    }

    @Test
    public void testGetPersonNotFound() {
        // Preparing test data
        long id = 1L;

        when(profileRepository.findById(id)).thenReturn(Optional.empty());

        // Performing the service method and verify the exception
        assertThrows(RuntimeException.class, () -> profileService.getPerson(id));

        // Verifying the repository method is called with the correct argument
        verify(profileRepository, times(1)).findById(id);
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    public void testDeleteProfile_ExistingProfile() {
        // Preparing test data
        long id = 1L;
        Profile profile = new Profile();
        profile.setId(id);

        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));

        // Performing the service method
        profileService.deleteProfile(id);

        // Verifying the repository method is called with the correct argument
        verify(profileRepository, times(1)).findById(id);
        verify(profileRepository, times(1)).delete(profile);
    }

    @Test
    public void testDeleteProfile_NonExistingProfile() {
        // Preparing test data
        long id = 1L;

        when(profileRepository.findById(id)).thenReturn(Optional.empty());

        // Performing the service method and verify the exception
        assertThrows(RuntimeException.class, () -> profileService.deleteProfile(id));

        // Verifying the repository method is called with the correct argument
        verify(profileRepository, times(1)).findById(id);
        verify(profileRepository, never()).delete(org.mockito.ArgumentMatchers.any(Profile.class));

    }


    @Test
    public void testGetProfileResponseDtos() {
        // Preparing test data
        Profile profile1 = new Profile();
        profile1.setId(1L);
        profile1.setName("John Doe");
        profile1.setEmail("john.doe@example.com");

        Profile profile2 = new Profile();
        profile2.setId(2L);
        profile2.setName("Jane Smith");
        profile2.setEmail("jane.smith@example.com");

        List<Profile> profiles = Arrays.asList(profile1, profile2);

        when(profileRepository.findAll()).thenReturn(profiles);

        ProfileResponseDto responseDto1 = new ProfileResponseDto();
        responseDto1.setId(1L);
        responseDto1.setName("John Doe");
        responseDto1.setEmail("john.doe@example.com");

        ProfileResponseDto responseDto2 = new ProfileResponseDto();
        responseDto2.setId(2L);
        responseDto2.setName("Jane Smith");
        responseDto2.setEmail("jane.smith@example.com");

        when(modelMapper.map(profile1, ProfileResponseDto.class)).thenReturn(responseDto1);
        when(modelMapper.map(profile2, ProfileResponseDto.class)).thenReturn(responseDto2);

        // Performing the service method
        List<ProfileResponseDto> responseDtos = profileService.getProfileResponseDtos();

        // Verifying the repository method is called
        verify(profileRepository, times(1)).findAll();

        // Verifying the modelMapper is called
        verify(modelMapper, times(1)).map(profile1, ProfileResponseDto.class);
        verify(modelMapper, times(1)).map(profile2, ProfileResponseDto.class);

        // Verifying the response
        assertThat(responseDtos, hasSize(2));

        ProfileResponseDto actualResponseDto1 = responseDtos.get(0);
        assertThat(actualResponseDto1.getId(), is(1L));
        assertThat(actualResponseDto1.getName(), is("John Doe"));
        assertThat(actualResponseDto1.getEmail(), is("john.doe@example.com"));

        ProfileResponseDto actualResponseDto2 = responseDtos.get(1);
        assertThat(actualResponseDto2.getId(), is(2L));
        assertThat(actualResponseDto2.getName(), is("Jane Smith"));
        assertThat(actualResponseDto2.getEmail(), is("jane.smith@example.com"));
    }
}
