package com.sparta.soomtut.util.security.oauth2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OAuthAttributesTest {
    private static final Map<String, Object> GOOGLE_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> NAVER_ATTRIBUTES = new HashMap<>();
    private static final Map<String, Object> KAKAO_ATTRIBUTES = new HashMap<>();
    private static final String GOOGLE_REGISTRATION_ID = "google";
    private static final String NAVER_REGISTRATION_ID = "naver";
    private static final String KAKAO_REGISTRATION_ID = "kakao";

    static {
        GOOGLE_ATTRIBUTES.put("sub", "1234567890");
        GOOGLE_ATTRIBUTES.put("email", "johndoe@example.com");
        GOOGLE_ATTRIBUTES.put("name", "John Doe");

        NAVER_ATTRIBUTES.put("response", new HashMap<>() {{
            put("id", "12345");
            put("email", "johndoe@example.com");
            put("name", "John Doe");
        }});

        KAKAO_ATTRIBUTES.put("id", "12345");
        KAKAO_ATTRIBUTES.put("kakao_account", new HashMap<>() {{
            put("email", "johndoe@example.com");
            put("profile", new HashMap<>() {{
                put("nickname", "John Doe");
            }});
        }});
    }

    @Test
    @DisplayName("구글 OAuth2 등록시 동작 성공")
    void testExtract_withValidGoogleRegistrationId_returnsCorrectAttributes() {
        Map<String, Object> expected = new HashMap<>();
        expected.put("attributes", GOOGLE_ATTRIBUTES);
        expected.put("providerid", "1234567890");
        expected.put("provider", "google");
        expected.put("email", "johndoe@example.com");
        expected.put("name", "John Doe");

        Map<String, Object> actual = OAuthAttributes.extract(GOOGLE_REGISTRATION_ID, GOOGLE_ATTRIBUTES);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("네이버 OAuth2 등록시 동작 성공")
    void testExtract_withValidNaverRegistrationId_returnsCorrectAttributes() {
        Map<String, Object> expected = new HashMap<>();
        expected.put("attributes", NAVER_ATTRIBUTES.get("response"));
        expected.put("providerid", "12345");
        expected.put("provider", "naver");
        expected.put("email", "johndoe@example.com");
        expected.put("name", "John Doe");

        Map<String, Object> actual = OAuthAttributes.extract(NAVER_REGISTRATION_ID, NAVER_ATTRIBUTES);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("카카오 OAuth2 등록시 동작 성공")
    void testExtract_withValidKakaoRegistrationId_returnsCorrectAttributes() {
        Map<String, Object> expected = new HashMap<>();
        expected.put("attributes", KAKAO_ATTRIBUTES.get("kakao_account"));
        expected.put("providerid", "12345");
        expected.put("provider", "kakao");
        expected.put("email", "johndoe@example.com");
        expected.put("name", "John Doe");

        Map<String, Object> actual = OAuthAttributes.extract(KAKAO_REGISTRATION_ID, KAKAO_ATTRIBUTES);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("불분명한 OAuth2 등록시 동작 실패")
    void testExtract_withInvalidRegistrationId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, 
            () -> OAuthAttributes.extract("invalid_registration_id", GOOGLE_ATTRIBUTES));
    }
}
