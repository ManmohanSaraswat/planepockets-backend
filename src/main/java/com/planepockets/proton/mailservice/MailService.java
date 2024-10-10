package com.planepockets.proton.mailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailService {

    @Autowired
    private Mailer mailer;

    @Autowired
    private TemplateEngine templateEngine;


    private ExecutorService executor = Executors.newFixedThreadPool(10); // Example with 10 threads

    public void sendMail(String subject, String body, String ... to) {
        Runnable mailSender = new MailSender(mailer, subject, body, to);
        executor.execute(mailSender);
    }

    public void sendHtmlMail(String subject, String body, String ... to) {
        Runnable mailSender = new MailSender(mailer, "HTML", subject, body, to);
        executor.execute(mailSender);
    }

    public void sendUserRegisteredMail(String fullName, String email) {
        Context context = new Context();
        context.setVariable("userName", fullName);
        String processedString = templateEngine.process("UserRegistered", context);
        sendHtmlMail("Registration Successful", processedString, email);
    }

    public void sendUserOtpPasswordReset(String fullName, String email, String otp) {
        Context context = new Context();
        context.setVariable("userName", fullName);
        context.setVariable("otpCode", otp);
        String processedString = templateEngine.process("ResetPasswordOtp", context);
        sendHtmlMail("Otp for Password Reset", processedString, email);
    }

    public void sendUserPasswordResetSuccessful(String fullName, String email) {
        Context context = new Context();
        context.setVariable("userName", fullName);
        String processedString = templateEngine.process("PasswordResetSuccessful", context);
        sendHtmlMail("Password reset successful", processedString, email);
    }

}
