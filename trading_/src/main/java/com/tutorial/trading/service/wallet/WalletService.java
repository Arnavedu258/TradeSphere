package com.tutorial.trading.service.wallet;

import com.tutorial.trading.model.Order;
import com.tutorial.trading.model.User;
import com.tutorial.trading.model.Wallet;

public interface WalletService {
    
    Wallet getUserWallet(User user);
   
    Wallet FindWalletById(Long id) throws Exception;
    Wallet WalletToWalletTranser(User sender,Wallet Reciever,Long amount) throws Exception;
    Wallet PayOrderPayment(Order order,User user) throws Exception;
    Wallet addbalancWallet(User user, long money);
    
    
}
