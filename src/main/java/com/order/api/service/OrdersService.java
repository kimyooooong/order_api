package com.order.api.service;

import com.order.api.component.CryptComponent;
import com.order.api.domain.Member;
import com.order.api.domain.Orders;
import com.order.api.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private final MemberService memberService;

    private final CryptComponent cryptComponent;

    public Orders save(Member member , String name) throws Exception {

        member = memberService.getOriginalMember("rladbdrrr");

        return ordersRepository.save(Orders.builder()
                .member(member)
                .orderNumber(cryptComponent.getOrderKey(12))
                .name(name)
                .build());
    }

}
