package com.tutorial.trading.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.trading.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserId(Long userId );

    
} 
