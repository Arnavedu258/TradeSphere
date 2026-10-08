package com.tutorial.trading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.tutorial.trading.model.coinEntity;

import tools.jackson.databind.JsonNode;

@Service
public class paramdatatrade {

    private  RestClient rest;

    public paramdatatrade(RestClient rest){
        this.rest=rest;
    }


	public coinEntity getCoinData(String id) {

        JsonNode responsed = rest.get()
       .uri(builder->builder
        .path("/coins/markets/")
        .queryParam("vs_currency","usd")
       .queryParam("ids",id)
        .queryParam("order", "market_cap_desc")
        
        .queryParam("per_page",100)
        .queryParam("page", 1)
        .queryParam("sparkline",false)
        .build())
        .retrieve()
        .body(JsonNode.class);
        
coinEntity coin=new coinEntity();

JsonNode response=responsed.get(0);
coin.setCoinId(response.get("id").asText());
coin.setSymbol(response.get("symbol").asText());
coin.setName(response.get("name").asText());
coin.setImage(response.get("image").asText());
coin.setCurrentPrice(response.get("current_price").asDouble());
coin.setMarketCap(response.get("market_cap").asDouble());
coin.setMarketCapRank(response.get("market_cap_rank").asInt());
coin.setPriceChangePercentage24h(response.get("price_change_percentage_24h").asDouble());


return coin;
	}


    
}
