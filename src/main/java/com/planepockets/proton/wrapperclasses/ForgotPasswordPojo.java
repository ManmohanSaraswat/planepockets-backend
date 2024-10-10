package com.planepockets.proton.wrapperclasses;

public class ForgotPasswordPojo {
    private String loginId;
    private String oldPassword;
    private String newPassword;
    private String otp;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public ForgotPasswordPojo(String loginId, String oldPassword, String newPassword, String otp) {
        this.loginId = loginId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.otp = otp;
    }
}
