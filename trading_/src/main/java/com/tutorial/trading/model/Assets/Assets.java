package com.tutorial.trading.model.Assets;

import com.tutorial.trading.model.User;
import com.tutorial.trading.model.coinEntity;

import jakarta.persistence.CascadeType;
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
@Table(name="Asset_table")
public class Assets {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private double BuyPrice;
    private double Quantity;
    
// bohot sara coin adapt by single person
    @ManyToOne(cascade = CascadeType.PERSIST)
    private coinEntity coin;


    @ManyToOne
// single person have many asset
    private User user;
    
}
