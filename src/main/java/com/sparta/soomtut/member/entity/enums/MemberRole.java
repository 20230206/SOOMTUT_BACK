package com.sparta.soomtut.member.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    MEMBER(Authority.MEMBER),
    ADMIN(Authority.ADMIN);

    private final String authority;

    public static class Authority{
        static String MEMBER = "ROLE_MEMBER";
        static String ADMIN = "ROLE_ADMIN";
    }
}
