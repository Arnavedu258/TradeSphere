package com.tutorial.userAuth.Service;

import java.nio.file.attribute.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tutorial.userAuth.Entity.UserEntity;
import com.tutorial.userAuth.Repository.UserRepo;

/**
 * MYUSerDetailServe
 */
@Service
public class MYUSerDetailServe implements UserDetailsService {


    @Autowired
    private UserRepo userrepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity UserSet=userrepo.findByUserName(username);

        if(UserSet == null){
            System.out.println("user data not exist kindly registry first");
            throw new UsernameNotFoundException(username);
        }
        return new Uerprincipal(UserSet);

    
    }

    public String userREeposave(UserEntity userEntity){
             if(userrepo.existsByUserName(userEntity.getUserName())){
            return "username";
        }

        if(userrepo.existsByUserEmail(userEntity.getUserEmail())){
            return "email";
        }

        BCryptPasswordEncoder emcoder=new BCryptPasswordEncoder(12);

      userEntity.setUserPassword(emcoder.encode(userEntity.getUserPassword()));
      
      userrepo.save(userEntity);
        return "success";

    }

}
