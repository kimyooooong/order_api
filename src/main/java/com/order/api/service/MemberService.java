package com.order.api.service;

import com.order.api.component.CryptComponent;
import com.order.api.domain.Member;
import com.order.api.form.JoinForm;
import com.order.api.form.LoginForm;
import com.order.api.repository.MemberRepository;
import com.order.api.utill.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;

    private final CryptComponent cryptComponent;

    /**
     * 비밀번호 - 단방향 암호화 SHA-256 , 그 외 정보 양방향 암호화 AES-256
     * @param joinForm
     * @return
     * @throws Exception
     */
    @Transactional
    public synchronized Member join(JoinForm joinForm) throws Exception {

        log.info("joinForm : {}" , joinForm);

        //값 발리데이션 체크.
        joinFromValidate(joinForm);

        return memberRepository.save(
                Member.builder()
                        .name(cryptComponent.encrypt(joinForm.getName()))
                        .nickName(cryptComponent.encrypt(joinForm.getNickname()))
                        .password(cryptComponent.getPasswordEncoder().encode(joinForm.getPassword()))
                        .phoneNumber(cryptComponent.encrypt(joinForm.getPhoneNumber()))
                        .email(cryptComponent.encrypt(joinForm.getEmail()))
                        .gender(cryptComponent.encrypt(joinForm.getGenderKind().toString()))
                        .build());
    }


    publoc Member login(LoginForm loginForm){

    }




    private void joinFromValidate(JoinForm joinForm) {
        ValidationUtils.isNamePattern(joinForm.getName());
        ValidationUtils.isNiceNamePattern(joinForm.getNickname());
        ValidationUtils.isPasswordPattern(joinForm.getPassword());
        ValidationUtils.isPhoneNumberPattern(joinForm.getPhoneNumber());
        ValidationUtils.isEmailPattern(joinForm.getEmail());
    }
}
