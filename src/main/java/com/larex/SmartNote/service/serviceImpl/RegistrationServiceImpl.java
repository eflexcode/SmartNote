package com.larex.SmartNote.service.serviceImpl;

import com.larex.SmartNote.entity.User;
import com.larex.SmartNote.entity.VerificationToken;
import com.larex.SmartNote.entity.wrapper.UserWrapper;
import com.larex.SmartNote.event.AuthEvent;
import com.larex.SmartNote.repository.UserRepository;
import com.larex.SmartNote.repository.VerificationRepository;
import com.larex.SmartNote.service.RegistrationService;
import com.larex.SmartNote.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
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
        BeanUtils.copyProperties(userWrapper, user);

//        user.setEmail(userWrapper.getEmail());
//        user.setPassword(userWrapper.getPassword());
//        user.setFirstName(userWrapper.getFirstName());
//        user.setLastName(user.getLastName());
//        user.setImageUrl("");

        userRepository.save(user);
        publisher.publishEvent(new AuthEvent(this, user.getEmail()));
        return "Verification token has been sent to email " + user.getEmail();
    }

    @Override
    public void createToken(String email) {

        User user = userRepository.findByEmail(email);
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setExpirationDate(new Date(System.currentTimeMillis()+Util.exp_time));
        verificationToken.setUser(user);

        // verification token should be sent to email here but am not doing that
        System.out.println("88888888888888888888888888888888888888888888888888888888888888888888");
        System.out.println(token);

        verificationRepository.save(verificationToken);

    }

    @Override
    public String verifyToken(String token) {

        VerificationToken verificationToken = verificationRepository.findByToken(token);
        Calendar calendar = Calendar.getInstance();

        if (verificationToken == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with token: " + token);
        }
        System.out.println("lllllllllllllllllllllllllll"+ (verificationToken.getExpirationDate().getTime() - calendar.getTime().getTime()));

        if (verificationToken.getExpirationDate().before(new Date()) ) {
            // expired

            verificationRepository.delete(verificationToken);

            return "Token Expired";
        } else {
            //Not expired
            log("lllllllllllllllllllllllllll"+ (verificationToken.getExpirationDate().getTime() - calendar.getTime().getTime()));
            User user = userRepository.findByEmail(verificationToken.getUser().getEmail());
            if (user == null) {
                return "No User Found With Token: " + token;
            }

            user.setEnable(true);
            userRepository.save(user);

        }

        return "Token Verified";
    }

    @Override
    public String expiredToken(String token) {

        VerificationToken verificationToken = verificationRepository.findByToken(token);
        if (verificationToken == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with token: " + token);
        }
        String newToken = UUID.randomUUID().toString();
        verificationToken.setToken(newToken);
        verificationToken.setExpirationDate(new Date(System.currentTimeMillis()+Util.exp_time));

        verificationRepository.save(verificationToken);
        // verification token should be sent to email here but am not doing that
        System.out.println("88888888888888888888888888888888888888888888888888888888888888888888");
        System.out.println(token);

        User user = userRepository.findByEmail(verificationToken.getUser().getEmail());
        if (user == null) {
            return "No User Found With Token: " + newToken;
        }

        return "A new verification token has being sent to email: "+user.getEmail();
    }
}
