package com.security.securityclient.events;

import org.springframework.context.ApplicationEvent;

import com.security.securityclient.models.RegenerationEvent;

import lombok.Getter;

import lombok.Setter;
@Getter
@Setter

public class NewTokenGenerationComplete extends ApplicationEvent{

   private RegenerationEvent regenerationEvent;
    public NewTokenGenerationComplete(RegenerationEvent regenerationEvent) {
        super(regenerationEvent);
        this.regenerationEvent = regenerationEvent;
        
       
    }
    
}
