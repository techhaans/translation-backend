package com.dc.api.controller;

import com.dc.facade.fd.LabelFacade;
import com.domain.dto.LabelResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/labels")
@Tag(name = "Label API", description = "Manage Labels and Translations")
public class LabelController {

   @Autowired
    private LabelFacade labelFacade;

    @PostMapping
    @Operation(summary = "Create or update labels and translations")
    public ResponseEntity<LabelResponseDTO> createOrUpdateLabels(
            @RequestHeader("customerId") Integer customerId,
            @RequestBody Map<String, String> labelKeyValuePairs) {

        LabelResponseDTO response = labelFacade.processLabels(customerId, labelKeyValuePairs);
        return ResponseEntity.ok(response);
    }




}
