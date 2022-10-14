package com.order.api.security;

import com.order.api.Model.CustomUserDetail;
import com.order.api.domain.Member;
import com.order.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetailService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException{

        Member member = null;
        try {
            member = memberService.getOriginalMember(username);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
        return member;
    }
}

