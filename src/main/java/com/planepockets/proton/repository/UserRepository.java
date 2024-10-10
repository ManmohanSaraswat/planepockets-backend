package com.planepockets.proton.repository;

import com.planepockets.proton.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "select * from user where email_id=?1", nativeQuery = true)
    public Optional<User> getUser(String userId);
}
