package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository <Profile, Long> {
}
