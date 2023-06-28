package com.security.securityclient.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
@Component
public class MailService {
    @Autowired
    private JavaMailSender emailSender;
    
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("davidmacharaimj@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        emailSender.send(message);
    }
    
    
}
