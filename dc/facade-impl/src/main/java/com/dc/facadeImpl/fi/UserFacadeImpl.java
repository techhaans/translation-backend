package com.dc.facadeImpl.fi;

import com.domain.dto.*;
import com.domain.enums.Role;
import com.domain.model.Customer;
import com.domain.model.ProofReader;
import com.domain.model.User;
import com.domain.repo.CustomerRepository;
import com.domain.repo.ProofReaderRepository;
import com.domain.service.UserService;
import com.dc.facade.fd.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final ProofReaderRepository proofReaderRepository;

    @Override
    public void registerCustomer(RegisterCustomerDTO dto) {
        userService.registerCustomer(dto);
    }

    @Override
    public void registerProofReader(RegisterProofReaderDTO dto) {
        userService.registerProofReader(dto);
    }

    @Override
    public String login(LoginDTO dto) {
        return userService.login(dto);
    }

    @Override
    public void forgotPassword(ForgotPasswordDTO dto) {
        userService.forgotPassword(dto);
    }

    @Override
    public void resetPassword(ResetPasswordDTO dto) {
        userService.resetPassword(dto);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Override
    public String getFullNameByUser(User user) {
        if (user.getRole() == Role.CUSTOMER) {
            Customer customer = customerRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            return customer.getFullName();
        } else if (user.getRole() == Role.PROOFREADER) {
            ProofReader proofReader = proofReaderRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Proofreader not found"));
            return proofReader.getFullName();
        }
        return null;
    }

}
