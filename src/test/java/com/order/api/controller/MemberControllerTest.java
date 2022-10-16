package com.order.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.api.domain.Member;
import com.order.api.domain.Orders;
import com.order.api.enums.GenderKind;
import com.order.api.form.JoinForm;
import com.order.api.form.LoginForm;
import com.order.api.service.MemberService;
import com.order.api.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MemberControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityManager entityManager;
    @Autowired
    ObjectMapper objectMapper;


    private JoinForm joinForm;

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrdersService ordersService;

    private Member member;

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

    @DisplayName("멤버 컨트롤러 테스트 - 회원 가입")
    @Test
    void memberControllerJoinTest() throws Exception {

        ResultActions resultActions = mockMvc.perform(post("/members/join")
                        .content(objectMapper.writeValueAsString(joinForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        resultActions.andExpect(status().isOk());

    }

    @DisplayName("멤버 컨트롤러 테스트 - 로그인")
    @Test
    void memberControllerLoginTest() throws Exception {

        //회원가입.
        memberService.join(joinForm);

        LoginForm loginForm = new LoginForm();
        loginForm.setLoginId(joinForm.getLoginId());
        loginForm.setPassword(joinForm.getPassword());

        ResultActions resultActions = mockMvc.perform(post("/members/login")
                        .content(objectMapper.writeValueAsString(joinForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        resultActions.andExpect(status().isOk());

    }

    @DisplayName("멤버 컨트롤러 테스트 - 단일 회원 상세 조회")
    @Test
    void memberControllerDetailTest() throws Exception {

        //회원가입.
        memberService.join(joinForm);

        ResultActions resultActions = mockMvc.perform(get("/members/"+joinForm.getLoginId()+"/detail")
                        .content(objectMapper.writeValueAsString(joinForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        resultActions.andExpect(status().isOk());

    }

    @DisplayName("멤버 컨트롤러 테스트 - 단일 회원 주문 조회")
    @Test
    void memberControllerOrdersTest() throws Exception {

        //회원가입.
        ordersService.save(memberService.join(joinForm) , "주문 성공");

        ResultActions resultActions = mockMvc.perform(get("/members/"+joinForm.getLoginId()+"/orders")
                        .content(objectMapper.writeValueAsString(joinForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        resultActions.andExpect(status().isOk());

    }


    @DisplayName("멤버 컨트롤러 테스트 - 여러 회원 조회")
    @Test
    void memberControllerAllMembersTest() throws Exception {

        //회원가입.
        ordersService.save(memberService.join(joinForm) , "주문 성공");

        String name = "김융훈임임";
        String email = "kimkim8371@gmail.com";

        ResultActions resultActions = mockMvc.perform(get("/members/all")
                        .content(objectMapper.writeValueAsString(joinForm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "20")
                        .param("name", name)
                        .param("email", email)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        resultActions.andExpect(status().isOk());

    }


}
