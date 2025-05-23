package com.dc.api.controller;

import com.dc.facade.fd.LanguageFacade;
import com.domain.dto.response.ApiResponse;
import com.domain.dto.response.ApiResponseBuilder;
import com.domain.dto.response.LanguageResponseDTO;
import com.domain.model.Language;
import com.domain.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/languages")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(
        name = "Language Management",
        description = "APIs for retrieving supported languages and languages by customer ID."
)
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageFacade languageFacade;
    private final LanguageService languageService;

    @Operation(summary = "Get All Languages")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<LanguageResponseDTO>>> getAllLanguages() {
        List<Language> languages = languageFacade.getAllLanguages();
        List<LanguageResponseDTO> dtoList = languages.stream()
                .map(LanguageResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponseBuilder.success(dtoList, "Languages fetched successfully"));
    }

    @Operation(summary = "Get Languages By Customer ID")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<LanguageResponseDTO>>> getLanguagesByCustomerId(@PathVariable Integer customerId) {
        List<Language> languages = languageFacade.getLanguagesByCustomerId(customerId);
        List<LanguageResponseDTO> dtoList = languages.stream()
                .map(LanguageResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponseBuilder.success(dtoList, "Languages fetched for customer successfully"));
    }
}
