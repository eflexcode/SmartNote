package com.larex.SmartNote.controller;

import com.larex.SmartNote.entity.wrapper.UserWrapper;
import com.larex.SmartNote.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
public class RegisterController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody UserWrapper userWrapper){

        return new ResponseEntity<String>(registrationService.registerUser(userWrapper), HttpStatus.CREATED);
    }

    @PostMapping("verifyToken")
    public ResponseEntity<String> verifyToken(@RequestParam(name = "token") String token){

        return new ResponseEntity<String>(registrationService.verifyToken(token),HttpStatus.OK);

    }

    @PostMapping("expiredToken")
    public ResponseEntity<String> expiredToken(@RequestParam(name = "old_token") String token){
        return new ResponseEntity<String>(registrationService.expiredToken(token),HttpStatus.OK);
    }

}
