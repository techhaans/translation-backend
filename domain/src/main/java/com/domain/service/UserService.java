package com.domain.service;

import com.domain.dto.*;
import com.domain.model.User;

public interface UserService {
     void registerCustomer(RegisterCustomerDTO dto);
     void registerProofReader(RegisterProofReaderDTO dto);
     String login(LoginDTO dto);

     void forgotPassword(ForgotPasswordDTO dto);

     void resetPassword(ResetPasswordDTO dto);

     User getUserByEmail(String email);
}

