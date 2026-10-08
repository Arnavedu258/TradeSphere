package com.tutorial.trading.model.order;

import com.tutorial.trading.model.OrderType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderReq {
    private String coinId;
    private double quantity;
    private OrderType orderType;
}
