package com.dc.api.controller;

import com.dc.facade.fd.CustomerLangFacade;
import com.domain.dto.CustomerLanguageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer/languages")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Customer Language API", description = "Fetch customer's supported languages")
public class CustomerLangController {

    private final CustomerLangFacade customerLangFacade;

    @Autowired
    public CustomerLangController(CustomerLangFacade customerLangFacade) {
        this.customerLangFacade = customerLangFacade;
    }

    @Operation(summary = "Get Customer Languages", description = "Returns a list of language codes and names for the given customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched languages"),
            @ApiResponse(responseCode = "400", description = "Invalid customer ID"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{customerUid}")
    public ResponseEntity<List<CustomerLanguageResponseDTO>> getLanguages(@PathVariable UUID customerUid) {
        List<CustomerLanguageResponseDTO> languages = customerLangFacade.fetchCustomerLanguages(customerUid);
        return ResponseEntity.ok(languages);
    }
}
