package com.sparta.soomtut.util.security.oauth2;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sparta.soomtut.util.security.UserDetailsImpl;
import com.sparta.soomtut.entity.Auth;
import com.sparta.soomtut.service.interfaces.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler  {

    private final AuthService authService;

    @Value("${endpoint.front}") private String ENDPOINT_FRONT;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    throws IOException, ServletException {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        int hash = user.hashCode();
        authService.saveAuth(Auth.builder().email(user.getUsername()).hash(hash).build());

        // 클라이언트에서 Query String을 이용하기위해서 다음처럼 URL을 구성해 준다.
        String frontend = ENDPOINT_FRONT + "/oauthlogin?name=" + user.getUsername() + "&role=" + user.getMember().getMemberRole() + "&hash=" + hash + "&state=" + user.getMember().isState();

        response.sendRedirect(frontend);
    }

}
