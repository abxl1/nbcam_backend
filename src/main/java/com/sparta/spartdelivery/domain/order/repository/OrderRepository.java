package com.sparta.spartdelivery.domain.order.repository;

import com.sparta.spartdelivery.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}