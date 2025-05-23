package com.dc.api.controller;

import com.domain.dto.response.ApiResponse;
import com.domain.service.TranslationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/translations")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Translation API", description = "Operations to fetch translations for customers")
@Validated
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @Operation(summary = "Get translations by customer UID and language code")
    @GetMapping("/{customerUid}/{langCode}")
    public ResponseEntity<ApiResponse<Map<String, String>>> getTranslationsByCustomerAndLang(
            @PathVariable("customerUid") String customerUid,
            @PathVariable("langCode")
            @Pattern(regexp = "^[a-z]{2}(-[A-Z]{2})?$", message = "Invalid language code format") String langCode) {

        Map<String, String> translations = translationService.getTranslations(customerUid, langCode);

        if (translations == null || translations.isEmpty()) {
            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    null,
                    "Translations not found for customerUid: " + customerUid + " and langCode: " + langCode,
                    "error"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<Map<String, String>> response = new ApiResponse<>(
                translations,
                "Translations fetched successfully",
                "success"
        );
        return ResponseEntity.ok(response);
    }
}
