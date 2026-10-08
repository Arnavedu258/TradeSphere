package com.tutorial.trading.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name="withdrawl-table")
public class Withdrawl {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long Id;

    private Long amount;

    private WithdrawlStatus STATUS;

    private LocalDateTime Date=LocalDateTime.now();

    @ManyToOne
    private User user;


    
}
