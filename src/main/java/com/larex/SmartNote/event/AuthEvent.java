package com.larex.SmartNote.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;


public class AuthEvent extends ApplicationEvent {

    private String email;

    public AuthEvent(Object source,String email) {
        super(source);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
