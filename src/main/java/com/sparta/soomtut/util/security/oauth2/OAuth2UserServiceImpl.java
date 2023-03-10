package com.sparta.soomtut.util.security.oauth2;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sparta.soomtut.member.entity.Member;
import com.sparta.soomtut.member.entity.enums.MemberState;
import com.sparta.soomtut.member.service.MemberService;
import com.sparta.soomtut.auth.dto.request.RegisterRequest;
import com.sparta.soomtut.location.entity.Location;
import com.sparta.soomtut.location.service.LocationService;
import com.sparta.soomtut.util.constants.Constants;
import com.sparta.soomtut.util.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

// lombok
@RequiredArgsConstructor

// jpa
@Service
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    
    private final MemberService memberService;
    private final LocationService locationService;
    private final PasswordEncoder passwordEncoder;

    private static final String PROVIDER = "provider";
    private static final String PROVIDERID = "providerid";
    private static final String EMAIL = "email";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        // 동의 및 계속하기를 통해 발급된 {localhost}/login/oauth2/code/{provider}/code={accesstoken} 를 통해 
        // 발급된 토큰을 security가 해당 url로 온 요청을 캐치하여 OAuth2UserRequest 객체를 생성해서 정보를 담아 보내준다.
        // 해당 토큰을 이용해서 DefaultOAuth2UserService의 loadUser에서 각각의 provider를 통해 유저 정보 요청을 보내준다.
        OAuth2User user = new DefaultOAuth2UserService().loadUser(request);
        String registrationId = request.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = OAuthAttributes.extract(registrationId, user.getAttributes());
        String provider = attributes.get(PROVIDER).toString();
        String providerId = attributes.get(PROVIDERID).toString();
        String nickname = provider + "_" + providerId;
        String uuid = UUID.randomUUID().toString().substring(0,6);
        String password = passwordEncoder.encode(Constants.STANDARD_OAUTH2_PASS + uuid);
        String email = attributes.get(EMAIL).toString() /* + "@" + provider */;
        Member member = memberService.findByProviderAndOauthEmail(provider, email)
                        .orElseGet( () -> createNewMember(email, nickname, password, provider));

        return new UserDetailsImpl(member, attributes);
    }

    private Member createNewMember(String email, String nickname, String password, String provider) {
        // email, password, nickname, provier
        var request = RegisterRequest.builder().email(email).nickname(nickname).provider(provider).providerId(email).build();
        Location location = locationService.saveLocation(new Location());
        Member member = Member.builder().request(request).location(location).build();
        member.updatePassword(password);
        member.changeState(MemberState.INIT);
        memberService.saveMember(member);
        
        return member;

    }
}
