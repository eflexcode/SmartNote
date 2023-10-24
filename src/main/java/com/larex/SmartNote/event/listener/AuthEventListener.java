package com.larex.SmartNote.event.listener;

import com.larex.SmartNote.event.AuthEvent;
import com.larex.SmartNote.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AuthEventListener implements ApplicationListener<AuthEvent> {

    @Autowired
    AuthService authService;

    @Override
    public void onApplicationEvent(AuthEvent event) {
        System.out.println("******************************************************************************************************");
        System.out.println(event.getEmail());
        authService.createToken(event.getEmail());
    }
}
