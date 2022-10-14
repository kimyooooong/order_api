package com.order.api.controller;

import com.order.api.common.RestResponse;
import com.order.api.domain.Member;
import com.order.api.form.JoinForm;
import com.order.api.form.OrderSaveForm;
import com.order.api.security.JwtTokenProvider;
import com.order.api.service.OrdersService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Log4j2
public class OrdersController {

    private final OrdersService ordersService;

    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation("( 토큰 필요 ) - 주문 등록")
    @PostMapping("/add")
    public ResponseEntity<RestResponse> add(
            @RequestHeader("Authentication") String Header,
            @RequestBody OrderSaveForm orderSaveForm
    ) throws Exception {

        Member member = (Member) jwtTokenProvider.getAuthentication(Header).getPrincipal();

        ordersService.save(member , orderSaveForm.getName());
        return ResponseEntity.ok(RestResponse.ok());
    }
    
    
    
    
}
