package com.tutorial.trading.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrderItem
 */

@Data

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Double quantity;

     // REST object only (NOT stored)
    @Transient 
    private coinEntity coinEntity;

    private String coinider;
    private String symbol;
    private String name;
    private String image;

    private double BuyPrice;
    private Double sellPrice;
     


    @JsonIgnore
    // issa jsonIgnore help to prevent recurrsion problem
    
    @OneToOne
      @JoinColumn(name = "order_id")
    private Order order;





}
