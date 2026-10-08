
package com.tutorial.trading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.trading.model.User;
import com.tutorial.trading.model.Withdrawl;
import java.util.List;

@Repository 
public interface WithdrawlRepository extends JpaRepository<Withdrawl,Long>{
    List<Withdrawl> findByUserId(Long user);


}