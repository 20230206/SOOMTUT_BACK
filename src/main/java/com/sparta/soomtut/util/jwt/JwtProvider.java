package com.sparta.soomtut.util.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sparta.soomtut.util.enums.MemberRole;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    
    public static final String AUTHORIZATION_HEADER = "Authorization"; // Header에 들어가는 key 값
    public static final String AUTHORIZATION_KEY = "auth"; // 사용자 권한의 key 값
    public static final String TOKEN_TYPE = "type";
    public static final String USER_EMAIL = "email";
    private static final String BEARER_PREFIX = "Bearer"; // Token 앞에 붙는 식별자


    @Value("${jwt.secret.key}") // application.properties에 지정한 key 값을 가져온다
    private String secretKey;
    private Key key; // 토큰을 생성할 때 넣어줄 key 값
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // key 객체를 암호화할 알고리즘

    
    @PostConstruct // 객체가 생성될 때 초기화 해주는 기능
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey); // Base64로 incoding 되어있는 상태를 decode 해주는 과정
        key = Keys.hmacShaKeyFor(bytes); // HMAC-SHA 알고리즘으로 디코드 된 바이트 값을 넣어 키를 생성
    }

    // 토큰 생성
    public String createToken(String username, MemberRole role, TokenType type) {
        Date date = new Date();
        if(key == null) this.init();
        String token = "";
        
        // 토큰 정보 추가
        Claims claims = Jwts.claims();
        claims.setSubject(username);
        claims.put(USER_EMAIL, username);
        claims.put(AUTHORIZATION_KEY, role);
        claims.put(TOKEN_TYPE, type);

        token = Jwts.builder()
                            .setClaims(claims)
                            .setExpiration(new Date(date.getTime() + type.getExpireTime())) // 토큰 유효기간 : 현재 시간 + TOKEN_TIME
                            .setIssuedAt(date) // 토큰이 언제 만들어졌는지
                            .signWith(key, signatureAlgorithm) // secret key를 이용해 만든 key 객체를 어떠한 알고리즘을 통해 암호화 할 것인지 지정
                            .compact();
        return token;
    }

    public String createToken(String refreshToken) {

        Claims claims = getUserInfoFromToken(refreshToken);
        String token = createToken((String)claims.get(USER_EMAIL), MemberRole.valueOf((String)claims.get(AUTHORIZATION_KEY)), TokenType.ACCESS);
        return token;
    }

    public String resolveToken(HttpServletRequest request) { // HttpServletRequest객체의 Header 안에 토큰이 들어있음

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER); // AUTHORIZATION_HEADER를 파라미터로 Header에 있는 Token 값을 가져온다

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX) && bearerToken.length() > 7) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); // 이렇게 코드를 넣으면 내부적으로 토큰을 검증
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않은 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰의 타입 확인    
    public TokenType getTokenType(String token) {
        Claims claims = getUserInfoFromToken(token);
        return TokenType.valueOf((String)claims.get(TOKEN_TYPE));
    }

    // 토큰에서 사용자 정보 가져오기 // 위에서 이미 토큰 검증을 완료했다는 가정이므로 이 토큰은 유효하기 때문에 try-catch문이 없다
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); // getBody를 통해 안에 정보들을 가져온다
    }

}
