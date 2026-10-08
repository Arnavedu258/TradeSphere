package com.tutorial.userAuth.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tutorial.userAuth.Entity.UserEntity;
import com.tutorial.userAuth.Service.MyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class RestCOntro {


    @Autowired
    private MyService serv;
    @PostMapping("/getaccess")
    public String register(@RequestBody UserEntity entity) {
       
        serv.registry(entity);
        return "entity km";
    }
    @GetMapping("/getaccess")
    public List<UserEntity> getMethod() {
       return   serv.getents();
       
    }
    
    
    
}
