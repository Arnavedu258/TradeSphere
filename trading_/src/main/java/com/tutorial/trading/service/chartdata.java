package com.tutorial.trading.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.tutorial.trading.model.coin7day;

import tools.jackson.databind.JsonNode;

@Service
public class chartdata {

    private RestClient restClient;
    private List<coin7day> li=new ArrayList<>();
     private LocalDateTime lastUpdated;

    public chartdata(RestClient restClient){
        this.restClient=restClient;
    }


  public List<coin7day> get7day(String id){


    if(!li.isEmpty() && lastUpdated !=null && lastUpdated.plusSeconds(10).isAfter(LocalDateTime.now())){
         System.out.println("7 data clerk");
        return li;
    }


try{
        JsonNode res=restClient.get()
    .uri(builder->builder
      .path("/coins/{id}/market_chart")
                        .queryParam("vs_currency", "usd")
                        .queryParam("days", 7)
                        .queryParam("interval", "daily")
                        .build(id))
                        .retrieve().body(JsonNode.class);
                        
    List<coin7day> price=new ArrayList<>();

    JsonNode response=res.get("prices");

    for(JsonNode part:response){
     coin7day charter=new coin7day();
 charter.setTime(part.get(0).asLong());
    charter.setPrice(part.get(1).asDouble());



price.add(charter);
    
    }

li=price;
    return price;
}
catch(Exception e){
    if(!li.isEmpty()){
        return li;
    }
    throw e;
}

  }
    
}
