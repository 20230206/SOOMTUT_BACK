package com.sparta.soomtut.config;

import com.sparta.soomtut.util.jwt.JwtProvider;
import com.sparta.soomtut.util.jwt.JwtAuthenticationFilter;
import com.sparta.soomtut.util.security.AccessDeniedHandlerImpl;
import com.sparta.soomtut.util.security.AuthdenticationEntryPointImpl;
import com.sparta.soomtut.util.security.UserDetailsServiceImpl;

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
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthdenticationEntryPointImpl authdenticationEntryPoint;
    

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

	@Bean
	public JwtAuthenticationFilter jwtVerificationFilter() {
		return new JwtAuthenticationFilter(jwtProvider, userDetailsService);
	}

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().loginPage("http://localhost:3000/signin")
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .allowedOrigins("http://localhost:3000");
    }
}
