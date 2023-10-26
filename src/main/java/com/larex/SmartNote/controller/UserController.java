package com.larex.SmartNote.controller;

import com.larex.SmartNote.entity.User;
import com.larex.SmartNote.entity.wrapper.UserWrapper;
import com.larex.SmartNote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<User> getUser() {
        return new ResponseEntity<User>(userService.getUser(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<User> update(@RequestBody UserWrapper user) {
        return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> delete() {

        userService.deleteUser();

        return new ResponseEntity<String>("Deleted", HttpStatus.OK);
    }
}