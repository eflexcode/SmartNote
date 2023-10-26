package com.larex.SmartNote.service;

import com.larex.SmartNote.entity.User;
import com.larex.SmartNote.entity.wrapper.UserWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  {

    User getUser();
    User updateUser(UserWrapper userWrapper);

    void deleteUser();
    UserDetailsService userDetailService();

    User getLoggedInUser();

}
