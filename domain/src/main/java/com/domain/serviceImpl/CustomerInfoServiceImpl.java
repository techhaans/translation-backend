package com.domain.serviceImpl;

import com.domain.dto.CustomerInfoDTO;
import com.domain.model.Customer;
import com.domain.model.Membership;
import com.domain.model.User;
import com.domain.repo.CustomerInfoRepository;
import com.domain.repo.MembershipRepository;
import com.domain.repo.UserRepository;
import com.domain.service.CustomerInfoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

    private final CustomerInfoRepository customerInfoRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    CustomerInfoServiceImpl(CustomerInfoRepository customerInfoRepository,UserRepository userRepository,MembershipRepository membershipRepository) {
        this.customerInfoRepository = customerInfoRepository;
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public CustomerInfoDTO findByProfileByCuid(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        Customer customer = customerInfoRepository. findByCuid(userId)
                .orElseThrow(() -> new EntityNotFoundException("No customer found for user " + userId));

//        User user = userRepository.findById(customer.getUser().getId())
//                .orElseThrow(() -> new EntityNotFoundException("No user found for id " + customer.getUser().getId()));

        Membership plan = membershipRepository.findById(customer.getMembership().getId())
                .orElseThrow(() -> new EntityNotFoundException("No membership found for id " + customer.getMembership()
                                    .getId()));

        return getCustomerInfoDTO(customer,plan);
    }

    private static CustomerInfoDTO getCustomerInfoDTO(Customer customer, Membership plan) {
        CustomerInfoDTO dto = new CustomerInfoDTO();

        dto.setFullName(customer.getFullName());
        dto.setCuid(customer.getCuid());
//        dto.setEmail(user.getEmail());
//        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setCompanyName(customer.getCompanyName());
        dto.setMembershipPlan(plan.getPlan());
        dto.setPlanExpiryDate(customer.getPlanExpiryDate());
        dto.setRegistrationDate(customer.getRegistrationDate());
        dto.setAccountStatus(customer.getAccountStatus());
        return dto;
    }

}
