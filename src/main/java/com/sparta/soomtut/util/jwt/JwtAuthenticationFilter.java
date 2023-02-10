package com.sparta.soomtut.util.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

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
		String token = jwtProvider.resolveToken(request);

		if (token != null) {

			if (!jwtProvider.validateToken(token)) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
			}

			Claims claims = jwtProvider.getUserInfoFromToken(token);
			UserDetails user = userDetailsService.loadUserByUsername(claims.getSubject());
			Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
				user.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
    }
}
