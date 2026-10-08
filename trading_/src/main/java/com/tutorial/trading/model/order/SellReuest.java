package com.tutorial.trading.model.order;

import com.tutorial.trading.model.User;
import com.tutorial.trading.model.coinEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor 
public class SellReuest {

    private coinEntity coin;
    private Long quantity;
    private User user;

    
}
