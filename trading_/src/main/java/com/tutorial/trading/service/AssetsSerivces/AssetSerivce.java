package com.tutorial.trading.service.AssetsSerivces;

import java.util.List;

import com.tutorial.trading.model.User;
import com.tutorial.trading.model.coinEntity;
import com.tutorial.trading.model.Assets.Assets;

public interface AssetSerivce {

    Assets createAsset(User user,coinEntity coin,double quantity);
    Assets getAssetByID(Long Assetid);
    Assets getAssetByUserIdAndId(Long USerID,Long Assetid);
    List<Assets> getUserAllAsset(Long USerID);
    // isma asset id jo barna ya kaam karna haa
    Assets updateAsset(Long Assetid,double quantity);
    Assets findAsssetByUserIdandCoinID(Long userId, String CoinEntity);
    void deleteAsset(Long AssetID);

      
    
}
