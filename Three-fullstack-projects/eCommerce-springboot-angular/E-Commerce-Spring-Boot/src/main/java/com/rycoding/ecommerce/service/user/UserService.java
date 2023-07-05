package com.rycoding.ecommerce.service.user;

import com.rycoding.ecommerce.dto.SignupDTO;
import com.rycoding.ecommerce.dto.UserDTO;

public interface UserService {
    UserDTO createUser(SignupDTO signupDTO);

    boolean hasUserWithEmail(String email);
}
