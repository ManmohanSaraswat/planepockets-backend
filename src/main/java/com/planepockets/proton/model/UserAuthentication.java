package com.planepockets.proton.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_authentication")
public class UserAuthentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loginId")
    private String loginId;

    @Column(name = "token")
    private String token;

    @Column(name = "otp")
    private String otp;

    @Column(name = "isToken")
    private Boolean isToken;

    public UserAuthentication(Long id, String loginId, String token, String otp) {
        this.id = id;
        this.loginId = loginId;
        this.token = token;
        this.otp = otp;
    }

    public UserAuthentication() {

    }

    public UserAuthentication(String loginId, String token) {
        this.loginId = loginId;
        this.token = token;
        this.isToken = true;
    }
    public UserAuthentication(String loginId, String otp, Boolean isToken) {
        this.loginId = loginId;
        this.otp = otp;
        this.isToken = isToken;
    }
    public UserAuthentication(String loginId, String token, String otp) {
        this.loginId = loginId;
        this.token = token;
        this.otp = otp;
        this.isToken = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setToken(Boolean token) {
        isToken = token;
    }
}
