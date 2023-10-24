package com.larex.SmartNote.controller;

import com.larex.SmartNote.entity.Login;
import com.larex.SmartNote.entity.wrapper.UserWrapper;
import com.larex.SmartNote.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${jwt.secret}")
    static String secret;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody UserWrapper userWrapper){

//        return new ResponseEntity<String>(registrationService.registerUser(userWrapper), HttpStatus.CREATED);
        return new ResponseEntity<String>(secret+"pppppppppppppppppppppp", HttpStatus.CREATED);
    }

    @PostMapping("verifyToken")
    public ResponseEntity<String> verifyToken(@RequestParam(name = "token") String token){

        return new ResponseEntity<String>(authService.verifyToken(token),HttpStatus.OK);

    }

    @PostMapping("expiredToken")
    public ResponseEntity<String> expiredToken(@RequestParam(name = "old_token") String token){
        return new ResponseEntity<String>(authService.expiredToken(token),HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody Login login){
        return new ResponseEntity<String>(authService.expiredToken(token),HttpStatus.OK);
    }

}
