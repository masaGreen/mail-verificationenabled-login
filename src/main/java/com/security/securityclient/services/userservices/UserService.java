package com.security.securityclient.services.userservices;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.security.securityclient.entities.User;
import com.security.securityclient.entities.VerificationToken;
import com.security.securityclient.repositories.UserRepository;
import com.security.securityclient.repositories.VerificationTokenReposoitory;


@Service

public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenReposoitory verificationTokenReposoitory;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    

    public User registerUser(User user){
       
        return  userRepository.save(user);
    }
    public void saveUserWithVerificationToken(VerificationToken token){
        verificationTokenReposoitory.save(token);
    }
    public String verifyUser(String token) {
        VerificationToken verificationToken = verificationTokenReposoitory.findByToken(token);
        if(verificationToken == null){
            return "invalid";
        }
        Calendar calendar = Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenReposoitory.delete(verificationToken);
            return "expired";
        }
        return "valid";
    }
    public String regenerateUserToken(String token) {
        VerificationToken verificationToken = verificationTokenReposoitory.findByToken(token);
         if(verificationToken == null){
            return "Expired token//login again";
        }
        String newToken = UUID.randomUUID().toString();
        verificationToken.setToken(newToken);
        verificationTokenReposoitory.save(verificationToken);
        return newToken;
       
    }
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }
    public Optional<User> findByPassword(String encpwd) {
        
        return userRepository.findByPassword(encpwd);
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
