package com.tutorial.trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.trading.model.OrderItem;

public interface OrderItemrepo extends JpaRepository<OrderItem,Long> {
    
}
