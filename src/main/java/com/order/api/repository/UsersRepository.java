package com.order.api.repository;

import com.todolist.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users , Long> {
    Optional<Users> findById(String id);
}
