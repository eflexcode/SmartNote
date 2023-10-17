package com.larex.SmartNote.service;

import com.larex.SmartNote.entity.wrapper.UserWrapper;

public interface RegistrationService {

    String registerUser(UserWrapper userWrapper);

    void createToken(String email);

    String verifyToken(String token);


}
