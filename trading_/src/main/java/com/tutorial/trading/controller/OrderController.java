package com.tutorial.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.trading.Repository.USerRepo;
import com.tutorial.trading.model.Order;
import com.tutorial.trading.model.OrderType;
import com.tutorial.trading.model.User;
import com.tutorial.trading.model.coinEntity;
import com.tutorial.trading.model.order.CreateOrderReq;
import com.tutorial.trading.model.order.SellReuest;
import com.tutorial.trading.service.concutrade;
import com.tutorial.trading.service.OrderServices.OrderService;
import com.tutorial.trading.service.wallet.WalletService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private WalletService walletservice;

    @Autowired
    private concutrade concurrent;

    @Autowired
    private USerRepo userrepo;

  @PostMapping("payment")
  public ResponseEntity<Order> createOrder(@RequestParam Long UserID,@RequestBody CreateOrderReq createorderReq) throws Exception{

    User user= userrepo.findById(UserID).orElseThrow(()->{
      return new RuntimeException("user not found");
    });

    coinEntity coin=concurrent.Particularcoin(createorderReq.getCoinId());

    Order order=orderService.ProcessOrder(coin,createorderReq.getQuantity(),createorderReq.getOrderType(),user);


return ResponseEntity.ok(order);

  }

  @GetMapping("/orders/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
Order order=orderService.GetorderBYID(id);

if(order.getUser().getId().equals(id)){
  // if user id match with order userid
  return ResponseEntity.ok(order);
}
else{
  // isma agar user id order is not match with user id  so 403 error
  return ResponseEntity.status(403).build();
}

  }

  @GetMapping("")
  public ResponseEntity<List<Order>> getALLOrder(@RequestParam Long USerID,@RequestParam OrderType OrderType,@RequestParam String Asset_Symbol) {
List<Order> orders=orderService.getallOrderOfUSer(USerID, OrderType, Asset_Symbol);

return ResponseEntity.ok(orders);


  }

  @PutMapping("sell")
  public ResponseEntity<Order> sellEntity( @RequestBody SellReuest req) throws Exception{
      Order order=orderService.sellAsset(req.getCoin() , req.getQuantity(), req.getUser());

      
      return ResponseEntity.ok(order);
      
  }
  
  
  
    
    
}
