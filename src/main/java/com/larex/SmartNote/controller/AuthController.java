package com.larex.SmartNote.controller;

import com.larex.SmartNote.entity.JwtResponse;
import com.larex.SmartNote.entity.Login;
import com.larex.SmartNote.entity.wrapper.UserWrapper;
import com.larex.SmartNote.service.AuthService;
import com.larex.SmartNote.service.UserService;
import com.larex.SmartNote.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.secret}")
    static String secret;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody UserWrapper userWrapper){

        return new ResponseEntity<String>(authService.registerUser(userWrapper), HttpStatus.CREATED);
//        return new ResponseEntity<String>(secret+"pppppppppppppppppppppp", HttpStatus.CREATED);
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
    public ResponseEntity<JwtResponse> login(@RequestBody Login login) throws DisabledException, BadCredentialsException {

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userService.userDetailService().loadUserByUsername(login.getEmail());
        return new ResponseEntity<JwtResponse>(new JwtResponse(jwtUtils.generateToken(userDetails)),HttpStatus.OK);
    }

}
