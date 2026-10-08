package com.tutorial.trading.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tutorial.trading.model.CandleDTO;
import com.tutorial.trading.model.coin7day;
import com.tutorial.trading.model.coinEntity;
import com.tutorial.trading.service.chartdata;
import com.tutorial.trading.service.concutrade;
import com.tutorial.trading.service.paramdatatrade;
import com.tutorial.trading.service.tradeServ;

import tools.jackson.databind.JsonNode;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class traController {


  @Autowired
  private tradeServ tradeService;


  @Autowired
  private paramdatatrade paramadata;

  @Autowired
  private chartdata chart;

  @Autowired
  private concutrade concurrent;

  @GetMapping("/marketdata")
  public Collection<coinEntity> getTradding(){
    return concurrent.getallcoin();
    


    

 }

 @GetMapping("/marketdata/{coinId}")
 public coinEntity getMethodName(@PathVariable String coinId) {

     return concurrent.Particularcoin(coinId);
     
 }


 @GetMapping("/charts/{coinId}")
 public List<CandleDTO> getchart(@PathVariable String coinId,@RequestParam(defaultValue = "30") int day) {

    
  return concurrent.getChartdata(coinId,day);




     

 
    
}

}

