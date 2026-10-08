package com.tutorial.trading.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.trading.model.Assets.Assets;

public interface Assetrepo extends JpaRepository<Assets,Long> {
      List<Assets> findByUserId(Long UserID);
      Assets findByUserIdAndId(Long UserId,Long AssetId);
    Assets findByUserIdAndCoinCoinId(Long UserID,String CoinID);

    boolean  existsById(Long AssetId);
    
}
