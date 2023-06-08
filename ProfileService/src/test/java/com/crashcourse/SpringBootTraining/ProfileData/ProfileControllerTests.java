package com.crashcourse.SpringBootTraining.ProfileData;

import com.crashcourse.SpringBootTraining.ProfileData.controller.ProfileController;
import com.crashcourse.SpringBootTraining.ProfileData.dto.ProfileRequestDto;
import com.crashcourse.SpringBootTraining.ProfileData.dto.ProfileResponseDto;
import com.crashcourse.SpringBootTraining.ProfileData.service.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Autowired
    private ProfileController profileController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    public void testCreateProfile() throws Exception {
        System.out.println("TEST: Test Create Profile - Starting!");
        // Prepare test data
        ProfileRequestDto requestDto = new ProfileRequestDto();
        requestDto.setName("John Doe");
        requestDto.setEmail("john.doe@example.com");
        requestDto.setMobNo("1234567890");

        ProfileResponseDto responseDto = new ProfileResponseDto();
        responseDto.setId(1L);
        responseDto.setName("John Doe");
        responseDto.setEmail("john.doe@example.com");

        when(profileService.createProfile(any(ProfileRequestDto.class))).thenReturn(responseDto);

        // Perform the request
        mockMvc.perform(post("/rest/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));

        // Verify the service method is called with the correct argument
        verify(profileService, times(1)).createProfile(any(ProfileRequestDto.class));
        System.out.println("TEST: Test Create Profile - Complete!");
    }

    // Utility method to convert object to JSON string
    private String asJsonString(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    @Test
    public void testGetProfile() throws Exception {
        System.out.println("TEST: Test Get Profile - Starting!");
        // Prepare test data
        long id = 1L;
        ProfileResponseDto responseDto = new ProfileResponseDto();
        responseDto.setId(id);
        responseDto.setName("John Doe");
        responseDto.setEmail("john.doe@example.com");

        when(profileService.getPerson(id)).thenReturn(responseDto);

        // Perform the request
        mockMvc.perform(get("/rest/profile/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));

        // Verify the service method is called with the correct argument
        verify(profileService, times(1)).getPerson(id);
        System.out.println("TEST: Test Get Profile - Complete!");
    }

    @Test
    public void testDeleteProfile() throws Exception {
        System.out.println("TEST: Test Delete Profile - Starting!");
        // Prepare test data
        long id = 1L;

        // Perform the request
        mockMvc.perform(delete("/rest/profile/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Profile deleted successfully"));

        // Verify the service method is called with the correct argument
        verify(profileService, times(1)).deleteProfile(id);
        System.out.println("TEST: Test Delete Profile - Complete!");
    }

    @Test
    public void testGetProfiles() throws Exception {
        System.out.println("TEST: Test Get Profiles - Starting!");
        // Prepare test data
        List<ProfileResponseDto> profiles = new ArrayList<>();
        ProfileResponseDto profile1 = new ProfileResponseDto();
        profile1.setId(1L);
        profile1.setName("John Doe");
        profile1.setEmail("john.doe@example.com");
        profiles.add(profile1);
        // Add more profiles if needed

        when(profileService.getProfileResponseDtos()).thenReturn(profiles);

        // Perform the request
        mockMvc.perform(get("/rest/profile/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(profiles.size())))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].email", is("john.doe@example.com")));
        // Add more assertions for additional profiles if needed

        // Verify the service method is called
        verify(profileService, times(1)).getProfileResponseDtos();
        System.out.println("TEST: Test Get Profiles - Complete!");
    }
}
