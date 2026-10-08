package com.tutorial.trading.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class walletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


        @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;
    // so isme we kniow ki one wallet have an many transaction so isliye
    @ManyToOne
    private Wallet wallet;
  private String coinImage;
  @Enumerated(EnumType.STRING)
    private WalletTransactionType walletTransactionType;
    

    private LocalDate date;
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    private String transferId;
    private String purpose;
    private Long amount;

    private String referenceId;

    
}
