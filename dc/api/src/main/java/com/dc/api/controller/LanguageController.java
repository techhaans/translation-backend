package com.dc.api.controller;

import com.dc.facade.fd.LanguageFacade;
import com.domain.model.Language;
import com.domain.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/language")
@CrossOrigin(origins = "http://localhost:3000")
public class LanguageController {

    @Autowired
    private LanguageFacade languageFacade;
    @Autowired
    private LanguageService languageService;

    @GetMapping("/api/languages")
    public List<Language> getLanguages() {
        return languageFacade.getAllLanguages();
    }
    @GetMapping("/{id}")
    public List<Language> getAllLanguages(@PathVariable Integer id)
    {
        return languageService.getLanguageByid(id);

    }
    @PutMapping("/update/{languageKey}")
    public ResponseEntity<Language> updateLanguage(@PathVariable String languageKey, @RequestBody Map<String ,String> requestBody)
    {
        String newName = requestBody.get("lname");
        Language updatedLanguage = languageService.updateLanguageName(languageKey, newName);
        return ResponseEntity.ok(updatedLanguage);
    }
}

