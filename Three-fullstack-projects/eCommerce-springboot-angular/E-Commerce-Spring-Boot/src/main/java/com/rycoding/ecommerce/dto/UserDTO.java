package com.rycoding.ecommerce.dto;

import com.rycoding.ecommerce.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String lastname;
    private String firstname;
    private String password;
    private UserRole userRole;
    private byte[] img;
}
