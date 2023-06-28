package com.security.securityclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.securityclient.entities.VerificationToken;

public interface VerificationTokenReposoitory extends JpaRepository<VerificationToken, Long>{

    VerificationToken findByToken(String token);
    
}
