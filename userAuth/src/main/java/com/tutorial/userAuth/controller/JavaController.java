package com.tutorial.userAuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tutorial.userAuth.Entity.UserEntity;
import com.tutorial.userAuth.Repository.UserRepo;
import com.tutorial.userAuth.Service.MYUSerDetailServe;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class JavaController {

    @Autowired
    private MYUSerDetailServe myusesfull;

    @Autowired 
    AuthenticationManager authenticationManager;


    @GetMapping("/login")
    public String loginSet() {

        // Authentication authentication=authenticationManager.authenticate(
        //     new UsernamePasswordAuthenticationToken(User.getUsername(), User.getpassword())
            
        // )

        return "login";

    }
     @GetMapping("/home")
    public String Home() {
        return "home";
    }
    
     @GetMapping("/register")
    public String registerPage() {
        return "signup";
    }

    @PostMapping("/register")
    public String postMethodName( UserEntity entity) {
    
       String passKey= myusesfull.userREeposave(entity);
        
       if(passKey.equals("email")){
        return "redirect:/register?error=email";
       }
    if( passKey.equals("username")){
         return "redirect:/register?error=username";
    }

      return "redirect:/login";

    }
    
    
}
