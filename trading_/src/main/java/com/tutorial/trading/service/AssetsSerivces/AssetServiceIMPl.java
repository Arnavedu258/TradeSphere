package com.tutorial.trading.service.AssetsSerivces;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.trading.Repository.Assetrepo;
import com.tutorial.trading.model.User;
import com.tutorial.trading.model.coinEntity;
import com.tutorial.trading.model.Assets.Assets;

@Service 
public class AssetServiceIMPl implements AssetSerivce {
    
    
    @Autowired
    private Assetrepo assetrepository;

 
    @Override
    public Assets createAsset(User user, coinEntity coin, double quantity) {

       Assets assseter=assetrepository.findByUserIdAndCoinCoinId(user.getId(),coin.getCoinId());


    if(assseter != null){
        double totalQuantity=assseter.getQuantity()+quantity;


        double oldPrice=assseter.getBuyPrice()*assseter.getQuantity();
        double currentlyprice=coin.getCurrentPrice()*quantity;
        double avgprice=(oldPrice+currentlyprice)/totalQuantity;

        assseter.setQuantity(totalQuantity);
        assseter.setBuyPrice(avgprice);


        return assetrepository.save(assseter);
    }

        Assets asset=new Assets();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setBuyPrice(coin.getCurrentPrice());
        asset.setQuantity(quantity);
      return   assetrepository.save(asset);
    }
    @Override
    public Assets getAssetByID(Long Assetid) {
       return assetrepository.findById(Assetid).orElseThrow(()->{
        return new RuntimeException("asset not found");
       });
    }
    @Override
    public Assets getAssetByUserIdAndId(Long USerID, Long Assetid) {
return assetrepository.findByUserIdAndId(USerID,Assetid);
    }

    @Override
    public List<Assets> getUserAllAsset(Long USerID) {
     return assetrepository.findByUserId(USerID);

        // now current price 

      
    }

    @Override
    public Assets updateAsset(Long Assetid, double quantity) {

        Assets oldAsset=getAssetByID(Assetid);
        // isma add karaga ya
        oldAsset.setQuantity(quantity+oldAsset.getQuantity());
return assetrepository.save(oldAsset);
    }

    @Override
    public Assets findAsssetByUserIdandCoinID(Long userId, String CoinEntity) {
            return assetrepository.findByUserIdAndCoinCoinId(userId, CoinEntity);
    }
    @Override
    public void deleteAsset(Long AssetID) {

        Assets deleteAsset=getAssetByID(AssetID);
        assetrepository.delete(deleteAsset);
    }   
}
