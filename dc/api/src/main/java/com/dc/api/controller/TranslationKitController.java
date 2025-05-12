package com.dc.api.controller;

import com.dc.facade.fd.TranslationKitFacade;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/kit")
//@RequiredArgsConstructor
public class TranslationKitController {

    private final TranslationKitFacade translationKitFacade;

    public TranslationKitController(TranslationKitFacade translationKitFacade) {
        this.translationKitFacade = translationKitFacade;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadTranslationKit(
            @RequestParam String customerId,
            @RequestHeader("customerUId") String customerUId) throws IOException {

        File zipFile = translationKitFacade.prepareTranslationKit(customerId, customerUId);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
        String fileName = "translation_kit_" + customerId + ".zip";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentLength(zipFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
