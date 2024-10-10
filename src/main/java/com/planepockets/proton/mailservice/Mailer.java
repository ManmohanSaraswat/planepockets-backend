package com.planepockets.proton.mailservice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Mailer {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String subject, String body, String ... to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendHtmlEmail(String subject, String body, String ... to) {
        MimeMessage message = mailSender.createMimeMessage();
        StringBuilder receiver = new StringBuilder();
        for (String mailId: to)
            receiver.append(mailId).append(",");
        receiver.deleteCharAt(receiver.length() - 1);
        try {
            message.setRecipients(MimeMessage.RecipientType.TO, receiver.toString());
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
