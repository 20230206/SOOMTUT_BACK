package com.sparta.soomtut.enums;

public enum MemberRole {
    MEMBER(Authority.MEMBER),
    ADMIN(Authority.ADMIN);

    private String authority;

    MemberRole(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public static class Authority{
        static String MEMBER = "ROLE_MEMBER";
        static String ADMIN = "ROLE_ADMIN";
    }
}
