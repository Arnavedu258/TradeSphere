package com.tutorial.trading.service.withdrawlService;

import java.util.List;

import com.tutorial.trading.model.User;
import com.tutorial.trading.model.Withdrawl;

public interface WithdrawlService {


    Withdrawl RequestWithDrawl(Long amount,User user);
    Withdrawl ProcessWithWithdrawl(Long withdrawlID,Boolean accept) throws Exception;
    List<Withdrawl> getUSerWithdrawlHistory(User user);
    List<Withdrawl> getAllWithdrawlRequest();
    
}
