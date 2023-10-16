package com.larex.SmartNote.service.serviceImpl;

import com.larex.SmartNote.entity.User;
import com.larex.SmartNote.entity.wrapper.UserWrapper;
import com.larex.SmartNote.repository.UserRepository;
import com.larex.SmartNote.service.RegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String registerUser(UserWrapper userWrapper) {

        User user = new User();
        BeanUtils.copyProperties(userWrapper,user);

//        user.setEmail(userWrapper.getEmail());
//        user.setPassword(userWrapper.getPassword());
//        user.setFirstName(userWrapper.getFirstName());
//        user.setLastName(user.getLastName());
//        user.setImageUrl("");

        userRepository.save(user);
        return "Success";
    }
}
