package com.sparta.soomtut.util.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.sparta.soomtut.util.security.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
     HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException 
    {
		// refresh token이 있는 쿠키정보 가져오기
		String refreshToken = extractCookie(request);

		// refresh 토큰이 유효하면, access token을 검증하는 로직으로 넘어간다.
		if(jwtProvider.validateToken(refreshToken)) {
			
			String accessToken = jwtProvider.resolveToken(request);

			// access token이 없거나, refresh token이 유효하고, access token이 유효하지 않으면 새로운 accesstoken을 생성시켜준다
			// 이경우에는, refresh token으로 로그인의 유효함을 확인 했으므로, 재검증 할 필요는 없다.
			if (accessToken == null || !jwtProvider.validateToken(accessToken)) {
				// refreshToken에 있는 정보를 활용하여, 새로운 access token을 만들어준다.
				accessToken = jwtProvider.createToken(refreshToken);
				response.addHeader(HttpHeaders.AUTHORIZATION, accessToken);
			}

			//access token이 존재하고, accesstoken이 유효하면 User의 정보를 가져온다.
			if (accessToken != null && jwtProvider.validateToken(accessToken)) {
				Claims claims = jwtProvider.getUserInfoFromToken(accessToken);
				UserDetails user = userDetailsService.loadUserByUsername((String)claims.get(JwtProvider.USER_EMAIL));
				Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
		}
		filterChain.doFilter(request, response);
    }

	private String extractCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("refresh".equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}

		return null;
	}
}
