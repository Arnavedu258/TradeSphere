package com.tutorial.trading.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Order
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

@ManyToOne
    private User user;

    @Column(nullable = false)
    private OrderType Ordertype;

    private LocalDateTime timestamp=LocalDateTime.now();

    private BigDecimal price;

    private OrderStatus status;

    // canscade we choose kyoki agar koi change happen in orderitem so change also reflect in order table



    // now in simple ya canscade used karega for dynamic
@OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
    private OrderItem orderItem;

}
