package com.security.securityclient.entities;


import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
    private static final int EXPIRESIN = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String token;
    private Date expirationTime;

    @OneToOne(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
     )
    @JoinColumn(
        name="id",
        nullable = false,
        foreignKey = @ForeignKey(name = "verification_id")
    )
    private User user;
   

    public VerificationToken(String token){
        this.token = token;
        this.expirationTime = calculateExpiry(EXPIRESIN);
    }
    public VerificationToken(User user,String token){
        this.token = token;
        this.expirationTime = calculateExpiry(EXPIRESIN);
    }


    private Date calculateExpiry(int ex) {
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(new Date().getTime());
        calender.add(Calendar.MINUTE, ex);
        return new Date(calender.getTime().getTime());
    }
}
