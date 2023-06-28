package com.security.securityclient.controllers.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.securityclient.entities.User;
import com.security.securityclient.events.NewTokenGenerationComplete;
import com.security.securityclient.events.RegisterCompleteEvent;
import com.security.securityclient.models.RegenerationEvent;
import com.security.securityclient.models.UserModel;
import com.security.securityclient.models.passwordResetModel;
import com.security.securityclient.services.userservices.UserService;

import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    PasswordEncoder encodePassword;

    @GetMapping
    public List<User> geAllUsers(){
        return userService.findAllUsers();
    }
    
    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest req){
        System.out.println("hello");
        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(encodePassword.encode(userModel.getPassword()));
        user.setRole("NORMAL");

        userService.registerUser(user);
        
       
        // registerEvent
        applicationEventPublisher.publishEvent(new RegisterCompleteEvent(user, 
        "http://"+req.getServerName()+":"+req.getServerPort()+"/"+req.getContextPath()));

    

        return "successfully registered";
    }
    @GetMapping("/verifytoken")
    public String verifyUser(@RequestParam("token") String token){
        
        String isVerified = userService.verifyUser(token);
        if(!isVerified.equalsIgnoreCase("valid")){
            return "unauthorized";
        }
        return "verified";
    }
    @GetMapping("/resendverifytoken")
    public String regenerateUserToken(@RequestParam("token") String token, HttpServletRequest req){
        
        
        String generateddToken = userService.regenerateUserToken(token);
        if(generateddToken.equalsIgnoreCase("Expired token//login again")){
            return "session expired login again";
        }
        String urlString = "http://"+req.getServerName()+":"+req.getServerPort()+"/"+req.getContextPath() ;
        applicationEventPublisher.publishEvent(new NewTokenGenerationComplete(new RegenerationEvent(generateddToken, urlString)  ));

        return "token regenarated and link sent";
    }
    @PostMapping("/changepassword")
    public String resetPassword( @RequestBody passwordResetModel passwordResetModel)
     {
        

        Optional<User> user = userService.findByEmail(passwordResetModel.getEmail());
       
        if(!user.isPresent()){
             return "bad request";
        }
        boolean isOldPasswordCorrect = encodePassword.matches(passwordResetModel.getOldPassword(), user.get().getPassword());
        if(isOldPasswordCorrect){
             user.get().setPassword(encodePassword.encode(passwordResetModel.getNewPassword()));
             userService.registerUser(user.get());
              return "reset password successful";
        }
        return "wrong old password";
       
       
    }
}
