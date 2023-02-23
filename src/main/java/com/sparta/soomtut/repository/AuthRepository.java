package com.sparta.soomtut.repository;

import com.sparta.soomtut.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long>{

    Optional<Auth> findByEmailAndHash(String email, int hash);
}
