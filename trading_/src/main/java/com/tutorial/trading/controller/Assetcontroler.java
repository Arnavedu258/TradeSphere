package com.tutorial.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.trading.Repository.USerRepo;
import com.tutorial.trading.model.User;
import com.tutorial.trading.model.Assets.Assets;
import com.tutorial.trading.service.AssetsSerivces.AssetSerivce;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/assets")
public class Assetcontroler {

    @Autowired
    private AssetSerivce assetSerivce;

    @Autowired 
    private USerRepo userRepository;


    @GetMapping("/{AssetId}")
    public ResponseEntity<Assets> getAssetbyID(@PathVariable Long AssetId) {
        Assets assets= assetSerivce.getAssetByID(AssetId);

        return ResponseEntity.ok(assets);

    }
@GetMapping("/coin/{Coinid}/useriD")
public ResponseEntity<Assets> getAssetByUserIDandCoinID(@PathVariable String Coinid,@RequestParam Long userID) {



    Assets assetss=assetSerivce.findAsssetByUserIdandCoinID(userID, Coinid);


    return ResponseEntity.ok(assetss);
}


@GetMapping()
public ResponseEntity<List<Assets>> getAssetforUSer(@RequestParam Long userid) {
 

    List<Assets> assetlist=assetSerivce.getUserAllAsset(userid);

    return ResponseEntity.ok(assetlist);
}



    
}
