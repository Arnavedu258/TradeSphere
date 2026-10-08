package com.tutorial.trading.controller;

import com.tutorial.trading.Repository.WithdrawlRepository;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tutorial.trading.Repository.USerRepo;
import com.tutorial.trading.model.User;
import com.tutorial.trading.model.Wallet;
import com.tutorial.trading.model.Withdrawl;
import com.tutorial.trading.service.wallet.WalletService;
import com.tutorial.trading.service.withdrawlService.WithdrawlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller 
@RequestMapping("/api/withdrawl")
public class withdrawlController{


    
    @Autowired 
    private USerRepo userrepository;
    @Autowired 
    private WithdrawlService withdrawlserivce;

    @Autowired  
    private WalletService walletService;

  @Autowired 
  private WithdrawlRepository withdrawlRepository;

    // now hum aab withdrawl request create karega

    @PostMapping("/withdrawl/{userid}/{amount}")
    public ResponseEntity<?> postWithdrawlRequest(@PathVariable Long userid,@PathVariable  Long amount) {
        //TODO: process POST request
        Optional<User> user=userrepository.findById(userid);

        // aaab ise we able to get user wallet
        Wallet walletgeter=walletService.getUserWallet(user.get());

        // fir withdrawl rew create 

        Withdrawl withdrawlreq=withdrawlserivce.RequestWithDrawl(amount,user.get());

        // aab we deducted amount

        walletService.addbalancWallet(user.get(),-withdrawlreq.getAmount());
        // now hum wallettransaction

        return ResponseEntity.ok(withdrawlreq);
    }
    

    @GetMapping("/admin/withdrawl")
    public ResponseEntity<List<Withdrawl>> getAllwithdrawlRequest() {
        List<Withdrawl> allwithdrawl=withdrawlserivce.getAllWithdrawlRequest();

        return ResponseEntity.ok(allwithdrawl);
    }


    @GetMapping("/withdrawl/{Userid}")
    public ResponseEntity<List<Withdrawl>> getWithdrawlHistory(@PathVariable Long Userid) {
       User user=userrepository.findById(Userid).orElseThrow(()->new RuntimeException("user not found"));

       List<Withdrawl> withhistory=withdrawlserivce.getUSerWithdrawlHistory(user);

       
return ResponseEntity.ok(withhistory);
    }

    
    @PatchMapping("/admin/withdrawl/{id}/process/{accept}")
    public ResponseEntity<?> updatewithdrawlstatus(@PathVariable Long id,@PathVariable  Boolean accept)throws Exception{
// isma hum firat process karega status of withdrawl

User user=userrepository.findById(id).get();
        Withdrawl withprocess=withdrawlserivce.ProcessWithWithdrawl(id, accept);

        // know hum wallet

     

        // isma update karega
        // if trasaction rejected so
// isma add balance used to time in post time withdrawl req me subtract from wallet isliye yaha laya becuase
// agar cancel the payment so showcase


        if(!accept){
        Wallet wallet=walletService.addbalancWallet(user,withprocess.getAmount());
        }
return ResponseEntity.ok(withprocess);
    }

    

    


}