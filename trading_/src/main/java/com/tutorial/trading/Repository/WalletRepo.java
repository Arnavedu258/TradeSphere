package com.tutorial.trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.trading.model.Wallet;


@Repository
public interface WalletRepo extends JpaRepository<Wallet,Long>{


    Wallet findByUserId(Long id);

    
}

