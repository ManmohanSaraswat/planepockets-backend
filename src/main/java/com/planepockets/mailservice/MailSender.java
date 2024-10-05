package com.planepockets.mailservice;

public class MailSender implements Runnable {

    private String[] to;
    private String subject;
    private String body;
    private Mailer mailer;
    private String mailType;

    public MailSender(Mailer mailer, String subject, String body, String[] to) {
        this.mailer = mailer;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.mailType = "SIMPLE";
    }

    public MailSender(Mailer mailer, String mailType, String subject, String body, String[] to) {
        this.mailer = mailer;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.mailType = mailType;
    }

    @Override
    public void run() {
        if(mailType.equals("SIMPLE"))
            mailer.sendMail(subject, body, to);
        else
            mailer.sendHtmlEmail(subject, body, to);
    }
}
