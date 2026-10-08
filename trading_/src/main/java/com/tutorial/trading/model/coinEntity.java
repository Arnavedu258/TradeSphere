package com.tutorial.trading.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "coinEntity")
public class coinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String coinId;
    private String symbol;
    private String name;
    private String image;

    private Double currentPrice;
    private Double marketCap;

    private Integer marketCapRank;

    private Double priceChangePercentage24h;

    public coinEntity orElseThrows(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrows'");
    }
    
}
