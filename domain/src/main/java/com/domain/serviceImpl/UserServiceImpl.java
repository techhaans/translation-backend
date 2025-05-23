package com.domain.serviceImpl;

import com.domain.dto.*;
import com.domain.enums.AccountStatus;
import com.domain.enums.Role;
import com.domain.exception.custom.*;
import com.domain.model.*;
import com.domain.repo.*;
import com.domain.security.jwt.JwtUtil;
import com.domain.service.EmailService;
import com.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final CustomerRepository customerRepo;
    private final ProofReaderRepository proofReaderRepo;
    private final UserRepository  userRepository;
    private final MembershipRepository membershipRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void registerCustomer(RegisterCustomerDTO dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered: " + dto.getEmail());
        }

        Membership membership = membershipRepo.findById(dto.getMembershipId())
                .orElseThrow(() -> new InvalidMembershipException("Invalid membership ID: " + dto.getMembershipId()));

        AccountStatus status;
        try {
            status = AccountStatus.valueOf(dto.getAccountStatus());
        } catch (IllegalArgumentException e) {
            throw new InvalidAccountStatusException("Invalid account status: " + dto.getAccountStatus());
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        userRepo.save(user);

        Customer customer = Customer.builder()
                .cuid(UUID.randomUUID())
                .user(user)
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .country(dto.getCountry())
                .companyName(dto.getCompanyName())
                .registrationDate(dto.getRegistrationDate() != null ? dto.getRegistrationDate() : LocalDate.now())
                .planExpiryDate(dto.getPlanExpiryDate())
                .accountStatus(AccountStatus.valueOf(dto.getAccountStatus()))
                .membership(membership)
                .build();
        customerRepo.save(customer);
        emailService.sendCustomerRegistrationEmail(user.getEmail(), dto.getFullName());

    }

    @Override
    public void registerProofReader(RegisterProofReaderDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match.");
        }
        if (!dto.isTermsAccepted()) {
            throw new TermsNotAcceptedException("Terms and conditions must be accepted.");
        }
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered: " + dto.getEmail());
        }

        String resumePath = null;
        if (dto.getResume() != null && !dto.getResume().isEmpty()) {
            resumePath = saveResumeFile(dto.getResume());
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.PROOFREADER)
                .build();
        userRepo.save(user);

        ProofReader proofReader = ProofReader.builder()
                .puid(UUID.randomUUID())
                .user(user)
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .supportedLanguages(String.join(",", dto.getSupportedLanguages()))
                .yearsOfExperience(dto.getYearsOfExperience())
                .availability(dto.getAvailability())
                .resumePath(resumePath)
                .build();
        proofReaderRepo.save(proofReader);
        emailService.sendProofReaderRegistrationEmail(user.getEmail(), dto.getFullName());
    }

    private String saveResumeFile(MultipartFile file) {
        try {
            String originalName = file.getOriginalFilename();
            if (originalName == null || !originalName.matches(".*\\.(pdf|doc|docx)$")) {
                throw new ResumeUploadException("Only PDF, DOC, and DOCX formats are supported.");
            }
            Path uploadDir = Paths.get("uploads", "resumes");
            Files.createDirectories(uploadDir);
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadDir.resolve(filename);
            file.transferTo(filePath);
            return filePath.toString();
        } catch (IOException e) {
            throw new ResumeUploadException("Resume upload failed. " + e.getMessage(), e);
        }
    }

    @Override
    public String login(LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
        } catch (BadCredentialsException ex) {
            System.err.println("Login failed for email: " + loginDTO.getEmail());
            throw new AuthenticationFailedException("Invalid email or password.");
        }

        // Fetch user from database
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new AuthenticationFailedException("User not found"));

        // Role validation
        if (!user.getRole().name().equalsIgnoreCase(loginDTO.getRole().name())) {
            throw new AuthenticationFailedException("Invalid role for this user.");
        }

        return jwtUtil.generateToken(user.getEmail()); // You can also include role in the token if needed
    }


    @Override
    public void forgotPassword(ForgotPasswordDTO dto) {
        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Email not found"));

        String token = UUID.randomUUID().toString();
        Timestamp expiry = Timestamp.valueOf(LocalDateTime.now().plusMinutes(15));

        user.setResetToken(token);
        user.setResetTokenExpiry(expiry);
        userRepo.save(user);

        // TODO: Send email with token (link) — placeholder
        System.out.println("Reset link: http://localhost:3000/reset-password?token=" + token);
        emailService.sendResetPasswordEmail(user.getEmail(), token);
    }

    @Override
    public void resetPassword(ResetPasswordDTO dto) {
        User user = userRepo.findByResetToken(dto.getToken())
                .orElseThrow(() -> new InvalidResetTokenException("Invalid reset token"));

        if (user.getResetTokenExpiry() != null && user.getResetTokenExpiry().before(new Timestamp(System.currentTimeMillis()))) {
            throw new InvalidResetTokenException("Reset token has expired");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepo.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("User not found with email: " + email));
    }



}
