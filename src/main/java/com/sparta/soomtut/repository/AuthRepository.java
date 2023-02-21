package com.sparta.soomtut.repository;

import  com.sparta.soomtut.entity.Auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long>{
    
}
