package com.dc.api.controller;

import com.dc.facade.fd.LabelFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/labels")
@Tag(name = "Label API", description = "Manage Labels and Translations")
public class LabelController {

   @Autowired
    private LabelFacade labelFacade;

    @GetMapping
    @Operation(summary = "Create or update labels and translations")
    public ResponseEntity<String> createOrUpdateLabels(
            @RequestHeader("default-language") String defaultLanguage,
            @RequestHeader("customer-id") Integer customerId,
            @RequestHeader("language-list") List<String> languageList,
            @RequestBody Map<String, String> labelData) {

        labelFacade.processLabels(customerId, defaultLanguage, languageList, labelData);
        return ResponseEntity.ok("Labels processed successfully");
    }


}
