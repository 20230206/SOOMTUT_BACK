package com.sparta.soomtut.config;

import com.sparta.soomtut.util.jwt.JwtProvider;
import com.sparta.soomtut.util.jwt.JwtAuthenticationFilter;
import com.sparta.soomtut.util.security.AccessDeniedHandlerImpl;
import com.sparta.soomtut.util.security.AuthdenticationEntryPointImpl;
import com.sparta.soomtut.util.security.UserDetailsServiceImpl;
import com.sparta.soomtut.util.security.oauth2.OAuth2UserServiceImpl;
import com.sparta.soomtut.util.security.oauth2.OAuth2AuthenticationSuccessHandlerImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthdenticationEntryPointImpl authdenticationEntryPoint;
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandlerImpl successHandler;

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
    public static final String ALLOWED_HEADERS_NAME = "" + HttpHeaders.AUTHORIZATION + "," + HttpHeaders.SET_COOKIE;

    @Value("${endpoint.back}") private String ENDPOINT_BACK;
    @Value("${endpoint.front}") private String ENDPOINT_FRONT;

    @Bean
    public JwtAuthenticationFilter jwtVerificationFilter() {
        return new JwtAuthenticationFilter(jwtProvider, userDetailsService);
    }

    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer() {
    //     return (web) -> web.ignoring().requestMatchers("/connect/**");
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin()
                .loginPage(ENDPOINT_BACK + "/auth/login")
                .defaultSuccessUrl(ENDPOINT_FRONT)
                .and()
                .oauth2Login(login -> login
                        .successHandler(successHandler)
                        .userInfoEndpoint()
                        .userService(oAuth2UserService)
                );

        http.cors()
                .configurationSource(corsConfigurationSource());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                    .requestMatchers("/static/**").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/connect/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtVerificationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling().authenticationEntryPoint(authdenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*", "Set-Cookie", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}