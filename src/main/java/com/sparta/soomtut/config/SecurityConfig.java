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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthdenticationEntryPointImpl authdenticationEntryPoint;
    private final OAuth2AuthenticationSuccessHandlerImpl successHandler;
    

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
    
	@Bean
	public JwtAuthenticationFilter jwtVerificationFilter() {
		return new JwtAuthenticationFilter(jwtProvider, userDetailsService);
	}

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
            .csrf().disable()
            .formLogin(login -> login
                .loginPage("http://localhost:3000/signin")
                .defaultSuccessUrl("http://localhost:3000/")
                .permitAll()
                )
            .oauth2Login(login -> login
                .successHandler(successHandler)
                .userInfoEndpoint()             // 로그인 성공 후 사용자 정보 획득
                .userService(oAuth2UserService) // 사용자 정보 처리 서비스 로직
            );
        
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated();

        http.exceptionHandling().authenticationEntryPoint(authdenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler);

        return http.build();
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .exposedHeaders("Authorization")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .allowedOrigins("http://localhost:3000");
    }
}
