package com.larex.SmartNote.service.serviceImpl;

import com.larex.SmartNote.entity.User;
import com.larex.SmartNote.entity.wrapper.UserWrapper;
import com.larex.SmartNote.repository.UserRepository;
import com.larex.SmartNote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser() {
        return getLoggedInUser();
    }

    @Override
    public User updateUser(UserWrapper userWrapper) {

        User user = getLoggedInUser();

        user.setLastName((userWrapper.getLastName() != null) ? userWrapper.getLastName() : user.getLastName());
        user.setFirstName((userWrapper.getFirstName() != null) ? userWrapper.getFirstName() : user.getFirstName());
        user.setEmail((userWrapper.getEmail() != null) ? userWrapper.getEmail() : user.getEmail());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser() {
        User user = getLoggedInUser();

        userRepository.delete(user);
    }

    @Override
    public UserDetailsService userDetailService() {

        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                System.out.println("lllllllllllllllllllllllllllllllllllllllllllllllllllllll "+username);
                return userRepository.findByEmail(username).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No User found"));
            }
        };
    }

    @Override
    public User getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("No User Found with id: "+email));
    }

}
