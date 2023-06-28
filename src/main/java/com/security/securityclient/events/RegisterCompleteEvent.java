package com.security.securityclient.events;


import org.springframework.context.ApplicationEvent;

import com.security.securityclient.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCompleteEvent extends ApplicationEvent {
    private String urlString;
    private User user;
    public RegisterCompleteEvent(User user,String urlString) {
        super(user);
        this.user = user;
        this.urlString = urlString;
    }

    
}
