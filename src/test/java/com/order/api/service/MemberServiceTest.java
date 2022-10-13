package com.order.api.service;

import com.order.api.component.CryptComponent;
import com.order.api.domain.Member;
import com.order.api.enums.GenderKind;
import com.order.api.form.JoinForm;
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

    @BeforeEach
    void setUp() {
    }

    @DisplayName("멤버 서비스 테스트 - 회원 가입")
    @Test
    void memberServiceJoinTest() throws Exception {

        JoinForm form = new JoinForm();
        form.setName("김융훈짱짱");
        form.setNickname("rladbdgns");
        form.setPassword("rladbdgns1234R!!!");
        form.setGenderKind(GenderKind.MALE);
        form.setEmail("kimkim8371@gmail.com");
        form.setPhoneNumber("010-9981-8371");

        Member member = memberService.join(form);

        assertThat(member.getName() , is(cryptComponent.encrypt(form.getName())));

    }
}
