package com.security.securityclient.events;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class NewTokenGenerationCompleteListener implements ApplicationListener<NewTokenGenerationComplete> {
    
    
    @Override
    public void onApplicationEvent(NewTokenGenerationComplete event) {
        System.out.println("seconbd listener ******");
      //generate url string and send to mail
        // String url = event.getRegenerationEvent().getUrl()+"user/verifytoken?token="+event.getRegenerationEvent().getToken();
       
    }
    
}
