package com.order.api.service;

import com.order.api.component.CryptComponent;
import com.order.api.domain.Member;
import com.order.api.enums.GenderKind;
import com.order.api.form.JoinForm;
import com.order.api.form.LoginForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CryptComponent cryptComponent;

    private Member member;

    private JoinForm joinForm;

    @BeforeEach
    void setUp() {
        joinForm = new JoinForm();
        joinForm.setLoginId("rladbdrrr");
        joinForm.setName("김융훈짱짱");
        joinForm.setNickname("rladbdgns");
        joinForm.setPassword("rladbdgns1234R!!!");
        joinForm.setGenderKind(GenderKind.MALE);
        joinForm.setEmail("kimkim8371@gmail.com");
        joinForm.setPhoneNumber("010-9981-8371");

    }

    @DisplayName("멤버 서비스 테스트 - 회원 가입")
    @Test
    void memberServiceJoinTest() throws Exception {

        Member member = memberService.join(joinForm);
        System.out.println(member);
        assertThat(member.getName() , is(cryptComponent.encrypt(joinForm.getName())));
    }

    @DisplayName("멤버 서비스 테스트 - 로그인")
    @Test
    void memberServiceLoginTest() throws Exception {

        //회원가입.
        memberService.join(joinForm);

        LoginForm loginForm = new LoginForm();
        loginForm.setLoginId("rladbdrrr");
        loginForm.setPassword("rladbdgns1234R!!!");

        Member member = memberService.login(loginForm);
    }
}
