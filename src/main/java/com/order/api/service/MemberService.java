package com.order.api.service;

import com.order.api.component.CryptComponent;
import com.order.api.domain.Member;
import com.order.api.exception.ServiceException;
import com.order.api.form.JoinForm;
import com.order.api.form.LoginForm;
import com.order.api.repository.MemberRepository;
import com.order.api.utill.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;

    private final CryptComponent cryptComponent;

    /**
     * 비밀번호 - 단방향 암호화 SHA-256 , 그 외 정보 양방향 암호화 AES-256
     * @param joinForm
     * @return 회원가입한 멤버.
     * @throws Exception
     */
    @Transactional
    public synchronized Member join(JoinForm joinForm) throws Exception {

        log.info("joinForm : {}" , joinForm);

        //값 발리데이션 체크.
        joinFromValidate(joinForm);

        return memberRepository.save(
            Member.builder()
                    .loginId(cryptComponent.encrypt(joinForm.getLoginId()))
                    .name(cryptComponent.encrypt(joinForm.getName()))
                    .nickName(cryptComponent.encrypt(joinForm.getNickname()))
                    .password(cryptComponent.getPasswordEncoder().encode(joinForm.getPassword()))
                    .phoneNumber(cryptComponent.encrypt(joinForm.getPhoneNumber()))
                    .email(cryptComponent.encrypt(joinForm.getEmail()))
                    .gender(cryptComponent.encrypt(joinForm.getGenderKind().toString()))
                    .build());
    }

    public Member login(LoginForm loginForm) throws Exception {

        Member member = getMember(loginForm.getLoginId());



        if(!cryptComponent.getPasswordEncoder().matches(loginForm.getPassword() , member.getPassword())){
            throw new ServiceException("패스워드가 올바르지 않습니다.");
        }

        return member;
    }

    public Member getMember(String loginId) throws Exception {

        Member member = memberRepository.findByLoginId(cryptComponent.encrypt(loginId)).orElseThrow(()-> new ServiceException("아이디가 존재 하지 않습니다."));

        //복호화.
        member.setLoginId(cryptComponent.decrypt(member.getLoginId()));
        member.setName(cryptComponent.decrypt(member.getName()));
        member.setNickName(cryptComponent.decrypt(member.getNickName()));
        member.setPhoneNumber(cryptComponent.decrypt(member.getPhoneNumber()));
        member.setEmail(cryptComponent.decrypt(member.getEmail()));
        member.setGender(cryptComponent.decrypt(member.getGender()));
        return member;
    }

    public Member getOriginalMember(String loginId) throws Exception {
        return memberRepository.findByLoginId(cryptComponent.encrypt(loginId)).orElseThrow(()-> new ServiceException("아이디가 존재 하지 않습니다."));
    }

    public void logout(){

    }




    private void joinFromValidate(JoinForm joinForm) throws Exception {

        ValidationUtils.isIdPattern(joinForm.getLoginId());
        ValidationUtils.isNamePattern(joinForm.getName());
        ValidationUtils.isNiceNamePattern(joinForm.getNickname());
        ValidationUtils.isPasswordPattern(joinForm.getPassword());
        ValidationUtils.isPhoneNumberPattern(joinForm.getPhoneNumber());
        ValidationUtils.isEmailPattern(joinForm.getEmail());

        if(memberRepository.findByLoginId(cryptComponent.encrypt(joinForm.getLoginId())).isPresent()){
            throw new ServiceException("동일한 아이디가 존재 합니다.");
        }

    }
}
