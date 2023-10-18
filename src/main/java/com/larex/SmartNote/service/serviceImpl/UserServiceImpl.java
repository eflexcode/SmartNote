package com.larex.SmartNote.service.serviceImpl;

import com.larex.SmartNote.entity.User;
import com.larex.SmartNote.entity.wrapper.UserWrapper;
import com.larex.SmartNote.repository.UserRepository;
import com.larex.SmartNote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not user found with id: " + id));
    }

    @Override
    public User updateUser(Long id, UserWrapper userWrapper) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not user found with id: " + id));

        user.setLastName((userWrapper.getLastName() != null) ? userWrapper.getLastName() : user.getLastName());
        user.setFirstName((userWrapper.getFirstName() != null) ? userWrapper.getFirstName() : user.getFirstName());
        user.setEmail((userWrapper.getEmail() != null) ? userWrapper.getEmail() : user.getEmail());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not user found with id: " + id));

        userRepository.deleteById(id);
    }
}
