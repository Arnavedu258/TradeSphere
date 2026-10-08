package com.tutorial.userAuth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.userAuth.Entity.UserEntity;
import java.util.List;


/**
 * UserRepo
 */
@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {

    UserEntity findByUserName(String userName);

    boolean existsByUserName(String userName);

     boolean existsByUserEmail(String userEmail);

}
