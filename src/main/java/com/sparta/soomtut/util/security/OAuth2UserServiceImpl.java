package com.sparta.soomtut.util.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sparta.soomtut.entity.Member;
import com.sparta.soomtut.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

// lombok
@RequiredArgsConstructor

// jpa
@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(request);

        String provider = request.getClientRegistration().getRegistrationId();
        String providerId = user.getAttribute("sub");
        String nickname = provider+"_"+providerId;
        
        String uuid = UUID.randomUUID().toString().substring(0,6);
        String password = passwordEncoder.encode("패스워드"+uuid);

        String email = user.getAttribute("email");
        
        Member member = memberRepository.findByEmail(email).orElse(
                    // 사용자가 해당 Email로 가입한 적이 없다면 새로 등록해준다.
                        memberRepository.save( Member.oauth2Register()
                                                     .email(email)
                                                     .password(password)
                                                     .nickname(nickname)
                                                     .provider(provider)
                                                     .providerId(providerId)
                                                     .build()));

        return new UserDetailsImpl(member, user.getAttributes());
    }


}
