package com.sparta.soomtut.util.security.oauth2;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthAttributes {
    GOOGLE("google", (attributes) -> {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("attributes", attributes);
        res.put("providerid", attributes.get("sub").toString());
        res.put("provider", "google");
        res.put("email", attributes.get("email").toString());
        res.put("name", attributes.get("name").toString());
        return res;
    }),
    NAVER("naver", (attributes) -> {
        Map<String, Object> res = new HashMap<String, Object>();
        var response = (Map<String, Object>)(attributes.get("response"));
        res.put("attributes", response);
        res.put("providerid", response.get("id").toString());
        res.put("provider", "naver");
        res.put("email", response.get("email").toString());
        res.put("name", response.get("name").toString());
        return res;
    }),
    KAKAO("kakao", (attributes) -> {
        Map<String, Object> res = new HashMap<String, Object>();
        var account = (Map<String, Object>)(attributes.get("kakao_account"));
        var profile = (Map<String, Object>)(account.get("profile"));
        res.put("attributes", account);
        res.put("providerid", attributes.get("id").toString());
        res.put("provider", "kakao");
        res.put("email", account.get("email").toString());
        res.put("name", profile.get("nickname").toString());
        return res;
    });

    private final String registrationId;
    private final Function<Map<String, Object>, Map<String, Object>> of;

    public static Map<String, Object> extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst().orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
