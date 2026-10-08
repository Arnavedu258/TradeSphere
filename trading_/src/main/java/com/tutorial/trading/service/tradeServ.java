package com.tutorial.trading.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.tutorial.trading.model.coinEntity;

import tools.jackson.databind.JsonNode;

@Service
public class tradeServ {

    private final RestClient restCLient;
    private LocalDateTime lastUpdated;

     private  List<coinEntity> coinCache=new ArrayList<>();
    public tradeServ(RestClient restClient){
        this.restCLient = restClient;


    }

    public List<coinEntity> getCoindata(){
 ;

      

        if( !coinCache.isEmpty() && lastUpdated !=null && lastUpdated.plusMinutes(2).isAfter(LocalDateTime.now())){
            System.out.println("Using cached data");
            return coinCache;
        }

try{
    JsonNode respose=restCLient.get()
        .uri(builder->builder
        .path("/coins/markets")
        .queryParam("vs_currency","usd")
        .queryParam("order", "market_cap_desc")
        .queryParam("per_page",100)
        .queryParam("page", 1)
        .queryParam("sparkline",false)
        .build())
        .retrieve()
        .body(JsonNode.class);

        
       List<coinEntity> coinList=new ArrayList<>();

        for(JsonNode coinNode :respose){

            coinEntity coin=new coinEntity();

            coin.setCoinId(coinNode.get("id").asText());
            coin.setSymbol(coinNode.get("symbol").asText());
            coin.setName(coinNode.get("name").asText());
            coin.setImage(coinNode.get("image").asText());
            coin.setCurrentPrice(coinNode.get("current_price").asDouble());
            coin.setMarketCap(coinNode.get("market_cap").asDouble());
            coin.setMarketCapRank(coinNode.get("market_cap_rank").asInt());
            coin.setPriceChangePercentage24h(coinNode.get("price_change_percentage_24h").asDouble());


            coinList.add(coin);

        }
       coinCache=coinList;
        lastUpdated=LocalDateTime.now();
            return coinCache;
}
catch(Exception e){

        System.out.println("CoinGecko unavailable, returning cached data");
    if(!coinCache.isEmpty()){
        return coinCache;

    }
    throw e;

}


    }
    
}
