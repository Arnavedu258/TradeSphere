package com.tutorial.userAuth.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tutorial.userAuth.Entity.UserEntity;

public class Uerprincipal implements UserDetails {

private UserEntity userEntity;

public Uerprincipal(UserEntity userEntity){
    this.userEntity=userEntity;
}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public @Nullable String getPassword() {
return userEntity.getUserPassword();

    }

    @Override
    public String getUsername() {
   return  userEntity.getUserName();
    }
    
}
