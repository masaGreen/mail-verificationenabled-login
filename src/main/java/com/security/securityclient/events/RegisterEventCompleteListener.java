package com.security.securityclient.events;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.security.securityclient.entities.User;
import com.security.securityclient.entities.VerificationToken;
import com.security.securityclient.services.userservices.UserService;
import com.security.securityclient.utils.MailService;

@Component
public class RegisterEventCompleteListener implements ApplicationListener<RegisterCompleteEvent >{
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    
    @Override
    public void onApplicationEvent(RegisterCompleteEvent event) {
        
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveUserWithVerificationToken(new VerificationToken(user,token));
        System.out.println("am being hailed");
        //send mail with token and link to verification end point
        String urlLink = event.getUrlString()+"user/verifytoken?token="+token;
         mailService.sendMail(user.getEmail(), "click below link to verify login", urlLink);
    }

    
}
