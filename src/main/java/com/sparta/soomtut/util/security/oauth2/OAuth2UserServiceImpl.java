package com.sparta.soomtut.util.security.oauth2;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.repository.MemberRepository;
import com.sparta.soomtut.util.constants.Constants;
import com.sparta.soomtut.util.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

// lombok
@RequiredArgsConstructor

// jpa
@Service
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = new DefaultOAuth2UserService().loadUser(request);
        
        String registrationId = request.getClientRegistration()
                                 .getRegistrationId();

        Map<String, Object> attributes = OAuthAttributes.extract(registrationId, user.getAttributes());

        String provider = attributes.get("provider").toString();
        String providerId = attributes.get("providerid").toString();

        String nickname = provider + "_" + providerId;

        String uuid = UUID.randomUUID().toString().substring(0,6);
        String password = passwordEncoder.encode(Constants.STANDARD_OAUTH2_PASS + uuid);

        
        String email = attributes.get("email").toString();

        Member member = memberRepository.findByProviderAndProviderId(provider, providerId).orElse(
            Member.oauth2Register().email(email).nickname(nickname).password(password).provider(provider).providerId(providerId).build()
        );

        return new UserDetailsImpl(member, attributes);
    }
}
