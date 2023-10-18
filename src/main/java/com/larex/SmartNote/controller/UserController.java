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

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<User>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserWrapper user) {
        return new ResponseEntity<User>(userService.updateUser(id, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        userService.deleteUser(id);

        return new ResponseEntity<String>("Deleted", HttpStatus.OK);
    }
}