package com.tutorial.userAuth.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tutorial.userAuth.Entity.UserEntity;
import com.tutorial.userAuth.Repository.UserRepo;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class MyService  {
    @Autowired
    private UserRepo repo;


    public UserEntity registry(UserEntity Entity){

      BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

      Entity.setUserPassword(encoder.encode(Entity.getUserPassword()));
     return  repo.save(Entity);



    }

    public List<UserEntity> getents(){
List<UserEntity> usd=repo.findAll();

        List<UserEntity> reps=new ArrayList<>();

        for(UserEntity uds:usd){
UserEntity userEntity=new UserEntity();
BeanUtils.copyProperties(usd,uds );

reps.add(uds);

        }
        return reps;

    }
}
