package com.planepockets.proton.repository;

import com.planepockets.proton.model.UserAuthentication;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from user_authentication where login_id=?1 and token=?2 and is_token = true", nativeQuery = true)
    public void deleteSession(String loginId, String token);

    @Query(value = "select * from user_authentication where login_id=?1 and token=?2 and is_token = true", nativeQuery = true)
    public UserAuthentication getSession(String loginId, String token);

    @Query(value = "select * from user_authentication where login_id=?1 and is_token = false limit 1", nativeQuery = true)
    public UserAuthentication getOtp(String loginId);

    @Transactional
    @Modifying
    @Query(value = "delete from user_authentication where login_id=?1 and is_token = false", nativeQuery = true)
    public void deleteOtp(String loginId);

    @Transactional
    @Modifying
    @Query(value = "insert into user_authentication (login_id, otp, is_token) values (?1, ?2, false)", nativeQuery = true)
    public void saveOtp(String loginId, String otp);

    @Transactional
    @Modifying
    @Query(value = "insert into user_authentication (login_id, token, is_token) values (?1, ?2, true)", nativeQuery = true)
    public void saveToken(String loginId, String token);
}
