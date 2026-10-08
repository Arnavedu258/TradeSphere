package com.tutorial.trading.service.OrderServices;

import java.util.List;

import com.tutorial.trading.model.Order;
import com.tutorial.trading.model.OrderItem;
import com.tutorial.trading.model.OrderType;
import com.tutorial.trading.model.User;
import com.tutorial.trading.model.coinEntity;

public interface OrderService {
  Order CreateOrder(User user,OrderItem orderItem,OrderType orderType);
    Order GetorderBYID(Long OrderID);
    List<Order> getallOrderOfUSer(Long userId,OrderType orderType,String AssetSymbol);
Order ProcessOrder(coinEntity coin,double quantity,OrderType orderType,User user)throws Exception;
Order sellAsset(coinEntity coin,double quantity,User user)throws Exception;


}
