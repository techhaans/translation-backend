package com.dc.api.controller;

import com.domain.dto.*;
import com.dc.facade.fd.UserFacade;
import com.domain.dto.response.ApiResponse;
import com.domain.dto.response.ApiResponseBuilder;
import com.domain.dto.response.AuthResponseDTO;
import com.domain.model.User;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Authentication", description = "Handles user authentication and registration")
public class UserController {

    private final UserFacade userFacade;

    @Operation(summary = "Register a new customer", description = "Registers a new customer user with required details.")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/register/customer"
    )
    public ResponseEntity<ApiResponse<String>> registerCustomer(@Valid @RequestBody RegisterCustomerDTO dto) {
        userFacade.registerCustomer(dto);
        return ResponseEntity.ok(ApiResponseBuilder.success(null, "Customer registered successfully."));
    }

    @Operation(summary = "Register a new proofreader", description = "Registers a new proofreader user, including file upload for resume.")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/register/proofreader",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<String>> registerProofReader(@Valid @ModelAttribute RegisterProofReaderDTO dto) {
        userFacade.registerProofReader(dto);
        return ResponseEntity.ok(ApiResponseBuilder.success(null, "Proofreader registered successfully."));
    }

    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token along with user info.")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/login"
    )
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = userFacade.login(loginDTO);
        User user = userFacade.getUserByEmail(loginDTO.getEmail());
        String fullName = userFacade.getFullNameByUser(user);

        AuthResponseDTO authResponse = AuthResponseDTO.builder()
                .token(token)
                .fullName(fullName)
                .email(user.getEmail())
                .role(user.getRole())
                .userId(user.getId())
                .message("Login successful")
                .build();

        return ResponseEntity.ok(ApiResponseBuilder.success(authResponse, "Login successful"));
    }

    @Operation(summary = "Forgot password", description = "Sends a password reset link to the user's email address.")
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody ForgotPasswordDTO dto) {
        userFacade.forgotPassword(dto);
        return ResponseEntity.ok(ApiResponseBuilder.success(null, "Reset password link sent to your email."));
    }

    @Operation(summary = "Reset password", description = "Resets the user password using a valid reset token.")
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        userFacade.resetPassword(dto);
        return ResponseEntity.ok(ApiResponseBuilder.success(null, "Password reset successful."));
    }

}
