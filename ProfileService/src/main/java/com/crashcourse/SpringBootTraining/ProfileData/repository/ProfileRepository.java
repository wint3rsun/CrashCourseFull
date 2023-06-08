package com.crashcourse.SpringBootTraining.ProfileData.repository;

import com.crashcourse.SpringBootTraining.ProfileData.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Optional<Profile> findByEmail(String email);
}
