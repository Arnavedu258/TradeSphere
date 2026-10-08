package com.tutorial.trading.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.trading.model.walletTransaction;


@Repository
public interface walletTransactionsrepo extends JpaRepository<walletTransaction,Long>{
List<walletTransaction> findBySenderIdOrReceiverIdOrderByCreatedAtDesc(
    Long senderId, Long receiverId
);

    
}
