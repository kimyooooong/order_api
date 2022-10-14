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
public class OrderServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private CryptComponent cryptComponent;

    private Member member;

    private JoinForm joinForm;

    @BeforeEach
    void setUp() throws Exception {

        //회원 가입.
        joinForm = new JoinForm();
        joinForm.setLoginId("rladbdrrr");
        joinForm.setName("김융훈짱짱");
        joinForm.setNickname("rladbdgns");
        joinForm.setPassword("rladbdgns1234R!!!");
        joinForm.setGenderKind(GenderKind.MALE);
        joinForm.setEmail("kimkim8371@gmail.com");
        joinForm.setPhoneNumber("010-9981-8371");

        memberService.join(joinForm);
    }
    @DisplayName("주문 서비스 테스트 - 주문 등록")
    @Test
    void ordersServiceSaveTest() throws Exception {

        Member member = memberService.getMember(joinForm.getLoginId());

        ordersService.save(member , "주문 성공");

    }
}
