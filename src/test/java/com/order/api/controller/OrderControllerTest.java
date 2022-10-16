package com.order.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.api.common.CommonMap;
import com.order.api.enums.GenderKind;
import com.order.api.form.JoinForm;
import com.order.api.form.LoginForm;
import com.order.api.form.OrderSaveForm;
import com.order.api.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityManager entityManager;
    @Autowired
    ObjectMapper objectMapper;

    String jwtToken;

    @Autowired
    private MemberService memberService;
    
    private JoinForm joinForm;

    @BeforeEach
    void setUp() throws Exception {
        joinForm = new JoinForm();
        joinForm.setLoginId("rladbdrrr");
        joinForm.setName("김융훈임임");
        joinForm.setNickname("rladbdgns");
        joinForm.setPassword("rladbdgns1234R!!!");
        joinForm.setGenderKind(GenderKind.MALE);
        joinForm.setEmail("kimkim8371@gmail.com");
        joinForm.setPhoneNumber("010-9981-8371");

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

        MvcResult result = resultActions.andReturn();
        CommonMap map = objectMapper.readValue(result.getResponse().getContentAsString(),CommonMap.class);
        LinkedHashMap<String,Object> maps = (LinkedHashMap) map.get("data");
        jwtToken = (String) maps.get("jwtToken");

        entityManager.clear();
    }


    @DisplayName("주문 컨트롤러 테스트 - 주문 등록")
    @Test
    void ordersServiceSaveTest() throws Exception {

        OrderSaveForm orderSaveForm = new OrderSaveForm();
        orderSaveForm.setName("주문성공");

        ResultActions resultActions = mockMvc.perform(post("/orders/add")
                        .content(objectMapper.writeValueAsString(orderSaveForm))
                        .header("Authentication" , jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        resultActions.andExpect(status().isOk());

    }

}
