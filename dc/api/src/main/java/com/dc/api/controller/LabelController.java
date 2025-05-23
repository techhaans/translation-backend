package com.dc.api.controller;

import com.dc.facade.fd.LabelFacade;
import com.domain.dto.LabelRequestDTO;
import com.domain.dto.response.LabelResponseDTO;
import com.domain.dto.response.ApiResponse;
import com.domain.model.Label;
import com.domain.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/labels")
@Tag(name = "Label API", description = "Manage Labels and Translations")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class LabelController {

    @Autowired
    private LabelFacade labelFacade;

    @Autowired
    private LabelService labelService;

    @PostMapping
    @Operation(summary = "Create or update labels and translations")
    public ResponseEntity<ApiResponse<LabelResponseDTO>> createOrUpdateLabels(
            @RequestHeader("customerUId") UUID customerCuid,
            @RequestBody @Validated LabelRequestDTO labelRequestDTO) {

        System.out.println("➡️ labelRequestDTO.getLabelKeyValuePairs() = " + labelRequestDTO.getLabelKeyValuePairs());
        LabelResponseDTO responseDTO = labelFacade.processLabels(customerCuid, labelRequestDTO.getLabelKeyValuePairs());

        ApiResponse<LabelResponseDTO> response = new ApiResponse<>(
                responseDTO,
                "Labels processed successfully",
                "success"
        );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{customerUId}")
    @Operation(summary = "Get labels by customer UID")
    public ResponseEntity<ApiResponse<LabelResponseDTO>> getLabelsByCustomerId(
            @PathVariable UUID customerUId) {

        LabelResponseDTO labelResponseDTO = labelFacade.getLabelTranslationsByCustomer(customerUId);

        ApiResponse<LabelResponseDTO> response = new ApiResponse<>(
                labelResponseDTO,
                "Labels retrieved successfully",
                "success"
        );

        return ResponseEntity.ok(response);
    }


}
