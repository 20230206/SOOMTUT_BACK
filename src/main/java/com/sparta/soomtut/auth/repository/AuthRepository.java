package com.sparta.soomtut.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.soomtut.auth.entity.Auth;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long>{

    Optional<Auth> findByEmailAndHash(String email, int hash);
}
