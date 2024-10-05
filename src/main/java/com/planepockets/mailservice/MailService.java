package com.planepockets.mailservice;

import com.planepockets.pojo.B2bPojo;
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

    public void sendUserInfoMail(String fullName, String email, B2bPojo pojo) {
        Context context = new Context();
        context.setVariable("userName", fullName);
        context.setVariable("from", pojo.getFrom());
        context.setVariable("to", pojo.getTo());
        context.setVariable("startDate", pojo.getStartDate());
        context.setVariable("endDate", pojo.getEndDate());
        String processedString = templateEngine.process("UserInfo", context);
        sendHtmlMail("New Request - " + fullName, processedString, email);
    }

}
