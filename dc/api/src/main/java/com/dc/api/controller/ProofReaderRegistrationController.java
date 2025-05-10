package com.dc.api.controller;
import com.dc.facade.fd.ProofReaderRegistrationFacade;
import com.domain.dto.ProofreaderRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proofreaders")
public class ProofReaderRegistrationController {
    @Autowired
    private ProofReaderRegistrationFacade proofreaderFacade;

    @PostMapping("/register")
    public ResponseEntity<String> registerProofreader(@RequestBody ProofreaderRegistrationRequest request) {
        proofreaderFacade.registerProofreader(request);
        return ResponseEntity.ok("Proofreader registered successfully");
    }

    @GetMapping
    public ResponseEntity<?> getAllProofreaders() {
        return ResponseEntity.ok(proofreaderFacade.getAllProofreaders());
    }
}
