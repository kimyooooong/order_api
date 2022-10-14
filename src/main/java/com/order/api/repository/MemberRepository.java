package com.order.api.repository;

import com.order.api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> , JpaSpecificationExecutor<Member> {

    Optional<Member> findByLoginId(String loginId);
}
