package com.order.api.repository;

import com.order.api.domain.Member;
import com.order.api.domain.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findByMemberOrderByOrderSeqDesc(Member member , Pageable pageable);
}
