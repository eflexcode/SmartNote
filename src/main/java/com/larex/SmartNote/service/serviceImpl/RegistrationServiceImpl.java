package com.larex.SmartNote.service.serviceImpl;

import com.larex.SmartNote.entity.User;
import com.larex.SmartNote.entity.VerificationToken;
import com.larex.SmartNote.entity.wrapper.UserWrapper;
import com.larex.SmartNote.event.AuthEvent;
import com.larex.SmartNote.repository.UserRepository;
import com.larex.SmartNote.repository.VerificationRepository;
import com.larex.SmartNote.service.RegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

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
        publisher.publishEvent(new AuthEvent(this,user.getEmail()));
        return "Success";
    }

    @Override
    public String createToken(String email) {

        User user = userRepository.findByEmail(email);
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setExpirationDate(Calendar.getInstance().getTime());
        verificationToken.setUser(user);

        // verification token should be sent to email here but am not doing that
        System.out.println("88888888888888888888888888888888888888888888888888888888888888888888");
        System.out.println(token);

        verificationRepository.save(verificationToken);

        return "Verification token has been sent to email "+ email;
    }
}
