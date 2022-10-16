package com.order.api.service;

import com.order.api.component.CryptComponent;
import com.order.api.domain.Member;
import com.order.api.enums.GenderKind;
import com.order.api.exception.ServiceException;
import com.order.api.form.JoinForm;
import com.order.api.form.LoginForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;

import java.util.List;

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
        joinForm.setName("김융훈임임");
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

        assertThat(member.getName() , is(joinForm.getName()));

    }

    @DisplayName("멤버 서비스 테스트 - 단일 멤버 가져오기 ( 복호화 )")
    @Test
    void memberServiceGetMemberTest() throws Exception {

        //회원가입.
        memberService.join(joinForm);

        Member member = memberService.getMember(joinForm.getLoginId());

        assertThat(member.getName() , is((joinForm.getName())));

    }


    @DisplayName("멤버 서비스 테스트 - 유저 전체 검색. ")
    @Test
    void memberServiceGetAllMemberTest() throws Exception {

        //회원가입.
        memberService.join(joinForm);

        int page = 1;
        int size = 20;

        String name = "김융훈임임";
        String email = "kimkim8371@gmail.com";

        List<Member> memberList = memberService.getAllMember(name , email , PageRequest.of(page-1 , size , Sort.by("memberSeq").descending()));

        assertThat(1, is((memberList.size())));

        Member findMember = memberList.get(0);

        assertThat(name, is((findMember.getName())));

    }

    @DisplayName("멤버 서비스 테스트 - 유저 회원가입 시 발리데이션 ")
    @Test
    void memberServiceJoinValidationTest() {

        //아이디 발리데이션.
        joinForm.setLoginId("asdf344fRR");
        Throwable exception = assertThrows(ServiceException.class, () -> memberService.join(joinForm));
        assertThat("아이디는 5~30자 사이의 영어 소문자 혹은 숫자만의 조합을 사용해주세요.", is(exception.getMessage()));
        joinForm.setLoginId("rladbdrrr");


        //이름 발리데이션.
        joinForm.setName("asdfFdfdfs444");
        exception = assertThrows(ServiceException.class, () -> memberService.join(joinForm));
        assertThat("이름은 5~20자 사이의 한글,영어 소문자로만 사용해주세요.", is(exception.getMessage()));
        joinForm.setName("김융훈임임");


        //닉네임 발리데이션.
        joinForm.setNickname("adfsadRR");
        exception = assertThrows(ServiceException.class, () -> memberService.join(joinForm));
        assertThat("닉네임은 5~30자 사이의 영어 소문자로만 사용해주세요.", is(exception.getMessage()));
        joinForm.setNickname("rladbdgns");

        //비밀번호 발리데이션
        joinForm.setPassword("ASDFASDF!!");
        exception = assertThrows(ServiceException.class, () -> memberService.join(joinForm));
        assertThat("비밀번호는 10~20자 사이에 영문 대문자,소문자,특수문자,숫자 각 1개이상 조합을 사용해주세요.", is(exception.getMessage()));
        joinForm.setPassword("rladbdgns1234R!!!");


        //핸드폰 번호 발리데이션
        joinForm.setPhoneNumber("010998143F");
        exception = assertThrows(ServiceException.class, () -> memberService.join(joinForm));
        assertThat("핸드폰 번호 형식이 올바르지 않습니다.", is(exception.getMessage()));
        joinForm.setPhoneNumber("010-9981-8371");


        //이메일 발리데이션
        joinForm.setEmail("asdf344fDFASDF");
        exception = assertThrows(ServiceException.class, () -> memberService.join(joinForm));
        assertThat("이메일 형식이 올바르지 않습니다.", is(exception.getMessage()));
        joinForm.setEmail("kimkim8371@gmail.com");



    }










}
