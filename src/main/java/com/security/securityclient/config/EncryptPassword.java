package com.security.securityclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@EnableWebSecurity
@Configuration
public class EncryptPassword {
    private static final String[] WHITELIST = {"/user/register", "/user", "/user/verifytoken","/user/resetpassword", 
    "/user/resendverifytoken", "/user/resetpassword"};

    @Bean
    PasswordEncoder encodePassword(){
        return new BCryptPasswordEncoder(10);
    }
    
   @Bean 
   SecurityFilterChain configure(HttpSecurity http) throws Exception
    {
        http
        .cors(cors->cors.disable())
        .csrf(csrf->csrf.disable())
        .authorizeHttpRequests(request-> request.requestMatchers(WHITELIST).permitAll()
        .anyRequest().authenticated()
        );

        return http.build();
        
    }

       
    
}
