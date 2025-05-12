package com.dc.api.controller;

import com.dc.facade.fd.LabelFacade;
import com.domain.dto.LabelResponseDTO;
import com.domain.model.Label;
import com.domain.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/labels")
@Tag(name = "Label API", description = "Manage Labels and Translations")
@CrossOrigin(origins = "http://localhost:3000")
public class LabelController {

    @Autowired
    private LabelFacade labelFacade;

    @Autowired
    private LabelService labelService;

    @PostMapping
    @Operation(summary = "Create or update labels and translations")
    public ResponseEntity<LabelResponseDTO> createOrUpdateLabels(
            @RequestHeader("customerUId") UUID customerCuid,
            @RequestBody Map<String, String> labelKeyValuePairs) {

        LabelResponseDTO response = labelFacade.processLabels(customerCuid, labelKeyValuePairs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public List<Label> getLabelsByCustomerId(@PathVariable("id") UUID customerCuid) {
        return labelService.getLabelsByCustomerCuid(customerCuid);
    }

}
