package com.tutorial.trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.trading.model.User;

@Repository
public interface USerRepo extends JpaRepository<User,Long>{
    
    
}
