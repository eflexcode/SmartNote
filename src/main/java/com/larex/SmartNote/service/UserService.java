package com.larex.SmartNote.service;

import com.larex.SmartNote.entity.User;
import com.larex.SmartNote.entity.wrapper.UserWrapper;

public interface UserService {

    User getUser(Long id);
    User updateUser(Long id, UserWrapper userWrapper);

    void deleteUser(Long id);

}
