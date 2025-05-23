package com.domain.dto;

import com.domain.enums.AccountStatus;
import com.domain.enums.MembershipPlan;
import com.domain.model.Membership;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerInfoDTO {

    private UUID cuid;

//  private String email;

    private String fullName;

//  private String phoneNumber;

    private String companyName;

    private MembershipPlan membershipPlan;

    private LocalDate planExpiryDate;

    private LocalDate registrationDate;

    private AccountStatus accountStatus;

}
