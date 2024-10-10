package com.planepockets.proton.service;

import com.planepockets.proton.model.UserAuthentication;
import com.planepockets.proton.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    public void logoutSession(String loginId, String token) {
        userAuthenticationRepository.deleteSession(loginId, token);
    }
    public Boolean checkSessionForUser(String loginId, String token) {
        UserAuthentication obj = userAuthenticationRepository.getSession(loginId, token);
        return obj != null;
    }

    public void saveToken(String loginId, String token) {
        userAuthenticationRepository.saveToken(loginId, token);
    }

    public void deleteOtp(String loginId) {
        userAuthenticationRepository.deleteOtp(loginId);
    }

    public void saveOtp(String loginId, String otp) {
        deleteOtp(loginId);
        userAuthenticationRepository.saveOtp(loginId, otp);
    }
    public UserAuthentication getOtp(String loginId) {
        return userAuthenticationRepository.getOtp(loginId);
    }
}
