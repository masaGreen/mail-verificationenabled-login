package com.security.securityclient.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class passwordResetModel {
    private String oldPassword;
    private String newPassword;
    private String email;
}
