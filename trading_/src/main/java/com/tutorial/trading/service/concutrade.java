package com.tutorial.trading.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.tutorial.trading.model.CandleDTO;
import com.tutorial.trading.model.coinEntity;

import tools.jackson.databind.JsonNode;

@Service
public class concutrade {

    private RestClient restClient;

      private  Map<String,coinEntity> concurrent=new ConcurrentHashMap<>();

      private Map<String,List<CandleDTO>>candlecache=new ConcurrentHashMap<>();

    concutrade(RestClient rest){
        this.restClient=rest;
    }
    

    @Scheduled(fixedRate = 10000)
    public void datamaker(){
try{

        JsonNode res=restClient.get()
         .uri(builder->builder
        .path("/coins/markets")
        .queryParam("vs_currency","usd")
        .queryParam("order", "market_cap_desc")
        .queryParam("per_page",100)
        .queryParam("page", 1)
        .queryParam("sparkline",false)
        .build()).retrieve().body(JsonNode.class);


        Map<String,coinEntity> maplet=new ConcurrentHashMap<>();

        for(JsonNode response:res){

            coinEntity coin=new coinEntity();


            
            coin.setCoinId(response.get("id").asText());
            coin.setSymbol(response.get("symbol").asText());
            coin.setName(response.get("name").asText());
            coin.setImage(response.get("image").asText());
            coin.setCurrentPrice(response.get("current_price").asDouble());
            coin.setMarketCap(response.get("market_cap").asDouble());
            coin.setMarketCapRank(response.get("market_cap_rank").asInt());
            coin.setPriceChangePercentage24h(response.get("price_change_percentage_24h").asDouble());


            maplet.put(coin.getCoinId(),coin);
            
        }
       
        
        concurrent.putAll(maplet);
    }
    catch(Exception e){
        System.out.println("now data failed");
        e.printStackTrace();
    }
        

        
    }
    public Collection<coinEntity> getallcoin(){

        return concurrent.values();

        
    }
    public coinEntity Particularcoin(String id){

        return concurrent.get(id);

        
    }



    public List<CandleDTO> getChartdata(String id,int day){

          String key = id + "-" + day;


        if(candlecache.containsKey(key)){
            return  candlecache.get(key);
        }
try{
        JsonNode resd=restClient.get()
        .uri(builder ->builder

             .path("/coins/{id}/ohlc")

            .queryParam("vs_currency", "usd")
            

            .queryParam("days", day)

            .build(id)
        ).retrieve().body(JsonNode.class);

        List<CandleDTO> TradeDto=new ArrayList<>();

        for(JsonNode row:resd){

            CandleDTO dto=new CandleDTO();

dto.setTime(row.get(0).asLong());

        dto.setOpen(row.get(1).asDouble());

        dto.setHigh(row.get(2).asDouble());

        dto.setLow(row.get(3).asDouble());

        dto.setClose(row.get(4).asDouble());

        TradeDto.add(dto);

        }

        candlecache.put(key, TradeDto);


        return TradeDto;    
    
} 
catch(Exception e){
    if( candlecache.containsKey(key)){
        return candlecache.get(key);
    }

    else{
        throw e;
}
}   






    }

     @Scheduled(fixedRate =1800000)

    public void clearChartCache() {

        candlecache.clear();

        System.out.println("Chart Cache Cleared");

    }

    
}

