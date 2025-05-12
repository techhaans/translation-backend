package com.dc.api.controller;

import com.domain.service.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/translations")
@CrossOrigin(origins = "http://localhost:3000")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/{customerUid}/{langCode}")
    public ResponseEntity<Map<String, String>> getTranslationsByCustomerAndLang(
            @PathVariable String customerUid,
            @PathVariable String langCode) {

        Map<String, String> translations = translationService.getTranslations(customerUid, langCode);
        return ResponseEntity.ok(translations);
    }
}

