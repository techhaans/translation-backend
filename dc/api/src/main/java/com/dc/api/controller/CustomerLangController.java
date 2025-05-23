package com.dc.api.controller;

import com.dc.facade.fd.CustomerLangFacade;
import com.domain.dto.CustomerLanguageRequestDTO;
import com.domain.dto.response.CustomerLanguageResponseDTO;
import com.domain.dto.response.ApiResponse;
import com.domain.dto.response.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customer/languages")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://techhaans.com")
@Tag(name = "Customer Language API", description = "Manage customer's supported languages")
public class CustomerLangController {

    private final CustomerLangFacade customerLangFacade;

    @PostMapping
    @Operation(summary = "Set Customer Languages", description = "Updates the supported languages for a customer")
    public ResponseEntity<ApiResponse<String>> setLanguages(@Valid @RequestBody CustomerLanguageRequestDTO requestDTO) {
        customerLangFacade.setCustomerLanguages(requestDTO);
        return ResponseEntity.ok(ApiResponseBuilder.success(null, "Customer languages updated successfully."));
    }

    @GetMapping("/{customerUid}")
    @Operation(summary = "Get Customer Languages", description = "Returns a list of language codes and names for the given customer ID")
    public ResponseEntity<ApiResponse<List<CustomerLanguageResponseDTO>>> getLanguages(@PathVariable UUID customerUid) {
        List<CustomerLanguageResponseDTO> languages = customerLangFacade.fetchCustomerLanguages(customerUid);
        return ResponseEntity.ok(ApiResponseBuilder.success(languages, "Fetched customer languages successfully."));
    }
}
