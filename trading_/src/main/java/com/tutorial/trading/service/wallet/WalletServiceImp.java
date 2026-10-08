package com.tutorial.trading.service.wallet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import com.tutorial.trading.Repository.WalletRepo;
import com.tutorial.trading.Repository.walletTransactionsrepo;
import com.tutorial.trading.model.Order;
import com.tutorial.trading.model.OrderType;
import com.tutorial.trading.model.User;
import com.tutorial.trading.model.Wallet;
import com.tutorial.trading.model.WalletTransactionType;
import com.tutorial.trading.model.walletTransaction;

import jakarta.transaction.Transactional;

@Service
public class WalletServiceImp implements WalletService {


    @Autowired
    private WalletRepo reposWalletRepo;

    @Autowired
    private walletTransactionsrepo wallrepo;
    @Override
    public Wallet getUserWallet(User user) {


        Wallet wallet=reposWalletRepo.findByUserId(user.getId());

        if(wallet ==null){
            wallet=new Wallet();
            wallet.setUser(user);

            wallet.setBalance(BigDecimal.ZERO);

            wallet=reposWalletRepo.save(wallet);
        }

return wallet;
    }

    @Override
    public Wallet addbalancWallet(User user, long money) {

        Wallet wallet=getUserWallet(user);
BigDecimal  balance =wallet.getBalance();
BigDecimal newBalance=balance.add(BigDecimal.valueOf(money)) ;

wallet.setBalance(newBalance);

walletTransaction tx = new walletTransaction();

tx.setSender(user);
tx.setReceiver(user);
tx.setWallet(wallet);

tx.setWalletTransactionType(WalletTransactionType.DEPOSIT);

tx.setDate(LocalDate.now());
tx.setCreatedAt(LocalDateTime.now());


tx.setTransferId("TRF" + System.currentTimeMillis());

tx.setPurpose("Wallet Deposit");

tx.setAmount(money);

tx.setReferenceId("TXN" + System.currentTimeMillis());

wallrepo.save(tx);

        return reposWalletRepo.save(wallet);
    }

    @Override
    public Wallet FindWalletById(Long id) throws Exception {


         Optional<Wallet> wallets=reposWalletRepo.findById(id);

        if(wallets.isPresent()){
      return wallets.get();
        }

  throw new Exception("wallet not founded");

    }
@Transactional
    @Override
    public Wallet WalletToWalletTranser(User sender, Wallet Recieverwallet, Long amount) throws Exception {

        Wallet sendWallet=getUserWallet(sender);

        if(sendWallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){

            throw new Exception("InSufficient balance ...");

        }

        BigDecimal balance=sendWallet
        .getBalance().
        subtract(BigDecimal.valueOf(amount)) ;

        sendWallet.setBalance(balance);

        reposWalletRepo.save(sendWallet);

        BigDecimal recievebalance=Recieverwallet.getBalance().add(BigDecimal.valueOf(amount));

        Recieverwallet.setBalance(recievebalance);


        reposWalletRepo.save(Recieverwallet);

    walletTransaction tx=new walletTransaction();
  tx.setSender(sender);
tx.setReceiver(Recieverwallet.getUser());
tx.setWallet(sendWallet);

tx.setWalletTransactionType(WalletTransactionType.WALLET_TRANSFER);

tx.setDate(LocalDate.now());
tx.setCreatedAt(LocalDateTime.now());

tx.setTransferId("TRF" + System.currentTimeMillis());

tx.setPurpose("Wallet to Wallet Transfer");

tx.setAmount(amount);

tx.setReferenceId("TXN" + System.currentTimeMillis());

wallrepo.save(tx);

return sendWallet;
    }
    @Override
    public Wallet PayOrderPayment(Order order, User user) throws Exception {

        Wallet payWallet=getUserWallet(user);

        if(order.getOrdertype().equals(OrderType.BUY)){

    if(payWallet.getBalance().compareTo(order.getPrice())<0){
        throw new Exception("insuffcient amount");
    }  
       BigDecimal newbalance=payWallet.getBalance().subtract(order.getPrice());
    payWallet.setBalance(newbalance);

        }else{
             BigDecimal newbalance=payWallet.getBalance().add(order.getPrice());

             payWallet.setBalance(newbalance);
        }
        reposWalletRepo.save(payWallet);

        walletTransaction tx = new walletTransaction();

tx.setSender(user);
tx.setReceiver(user);
tx.setWallet(payWallet);

tx.setWalletTransactionType(
    order.getOrdertype() == OrderType.BUY
        ? WalletTransactionType.ORDER_PAYMENT
        : WalletTransactionType.ORDER_RECEIVE
);


tx.setDate(LocalDate.now());
tx.setCreatedAt(LocalDateTime.now());   // ADD THIS
tx.setTransferId("ORD" + order.getId());

tx.setPurpose("Order #" + order.getId());

tx.setAmount(order.getPrice().longValue());

tx.setReferenceId("TXN" + System.currentTimeMillis());

wallrepo.save(tx);

        return payWallet;
    } 
}
