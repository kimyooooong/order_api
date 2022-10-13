package com.order.api.repository;

import com.order.api.domain.Member;
import com.order.api.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
