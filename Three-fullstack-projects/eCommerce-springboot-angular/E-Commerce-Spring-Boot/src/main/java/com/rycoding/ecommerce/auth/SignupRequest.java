package com.rycoding.ecommerce.auth;

import com.rycoding.ecommerce.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private UserRole userRole;
    private byte[] img;
}

