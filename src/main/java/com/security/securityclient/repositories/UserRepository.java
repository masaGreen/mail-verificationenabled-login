package com.security.securityclient.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.securityclient.entities.User;


public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByPassword(String encpwd);

    Optional<User> findByEmail(String email);
    
}
