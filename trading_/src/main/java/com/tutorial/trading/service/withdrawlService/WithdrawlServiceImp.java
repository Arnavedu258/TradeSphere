package com.tutorial.trading.service.withdrawlService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.trading.Repository.WithdrawlRepository;
import com.tutorial.trading.model.User;
import com.tutorial.trading.model.Withdrawl;
import com.tutorial.trading.model.WithdrawlStatus;
import com.tutorial.trading.service.wallet.WalletService;

@Service 
public class WithdrawlServiceImp implements WithdrawlService {

    @Autowired 
    private WithdrawlRepository withrepo;

    @Autowired 
    private WalletService walletService;
    @Override
    public Withdrawl RequestWithDrawl(Long amount, User user) {

        Withdrawl reqWithdrawl=new Withdrawl();
        reqWithdrawl.setAmount(amount);
        reqWithdrawl.setUser(user);
        reqWithdrawl.setSTATUS(WithdrawlStatus.PENDING);
        // TODO Auto-generated method stub
        return withrepo.save(reqWithdrawl);
    }

    @Override
    public Withdrawl ProcessWithWithdrawl(Long withdrawlID, Boolean accept) throws Exception {


        Optional<Withdrawl> withdrawlsd=Optional.ofNullable(withrepo.findById(withdrawlID).orElseThrow(()->{
            return  new RuntimeException("withdraw not found");
        }));
        

        Withdrawl withtrans=withdrawlsd.get();
        if(withtrans.getSTATUS() != WithdrawlStatus.PENDING){
            throw new Exception("withdrawn alread processing");
        }
        withtrans.setDate(LocalDateTime.now());
        if(accept){
            withtrans.setSTATUS(WithdrawlStatus.SUCCESS);
            
        }
        else{
            withtrans.setSTATUS(WithdrawlStatus.DECLINED);

            walletService.addbalancWallet(withtrans.getUser(),withtrans.getAmount());
        }
return withrepo.save(withtrans);

    }

    @Override
    public List<Withdrawl> getUSerWithdrawlHistory(User user) {
       return withrepo.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawl> getAllWithdrawlRequest() {
       return withrepo.findAll();
    }
    
}
