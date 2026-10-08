package com.tutorial.trading.controller;


import com.tutorial.trading.config.ApiConfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tutorial.trading.Repository.OrderRepository;
import com.tutorial.trading.Repository.USerRepo;
import com.tutorial.trading.Repository.walletTransactionsrepo;
import com.tutorial.trading.model.User;
import com.tutorial.trading.model.Order;
import com.tutorial.trading.model.Wallet;
import com.tutorial.trading.model.walletTransaction;
import com.tutorial.trading.service.wallet.WalletService;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("api/wallet")

public class WalletController {

@Autowired
private WalletService service;

@Autowired
private USerRepo userRepository;

@Autowired
private OrderRepository orderRepository;

@Autowired
private walletTransactionsrepo waltrepo;


    @GetMapping
    public ResponseEntity<Wallet> getwallet(@RequestParam Long userID) {


        User user=userRepository.findById(userID)
        .orElseThrow(()->
           new RuntimeException("user not found"));
        Wallet  wallet=service.getUserWallet(user);
        return ResponseEntity.ok(wallet);
    }
    
@PutMapping("transfer")

    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestParam Long SenderId,@RequestParam Long RecieverID,@RequestBody walletTransaction walletTransaction ) throws Exception{


        User Sender=userRepository.findById(SenderId).orElseThrow(()->
new RuntimeException("sender not found")
     
    );
           User Reciever=userRepository.findById(RecieverID).orElseThrow(()->
new RuntimeException("Receiver User ID not found")
    
    );

Wallet recieverWallet=service.getUserWallet(Reciever);


Wallet senderwWallet=service.WalletToWalletTranser(Sender,recieverWallet,walletTransaction.getAmount());




        return ResponseEntity.ok(senderwWallet);
    }

@PutMapping("deposit")
public ResponseEntity<Wallet> depositamount( @RequestParam Long userID,
        @RequestParam Long amount) {

   User user=userRepository.findById(userID).orElseThrow(()->
new RuntimeException("not user exist")
   );
   Wallet depowallet=service.addbalancWallet(user, amount);

   return ResponseEntity.ok(depowallet);
}


    @PutMapping("/pay")
    public ResponseEntity<Wallet> PAYforPayment(@RequestParam Long userID,@RequestParam Long orderID) throws Exception{
      
        User user=userRepository.findById(userID).orElseThrow(()->
    new RuntimeException("not user find"));

Order order=orderRepository.findById(orderID).orElseThrow(()->
    new ResponseStatusException(HttpStatus.NOT_FOUND,"not found here"));

Wallet updateWallet=service.PayOrderPayment(order,user);


   
    return ResponseEntity.ok(updateWallet);
}

@GetMapping("transaction")
public ResponseEntity<List<walletTransaction>> getMethodName(@RequestParam Long userID) {
   return ResponseEntity.ok(waltrepo.findBySenderIdOrReceiverIdOrderByCreatedAtDesc(userID, userID));
    
   
}




}