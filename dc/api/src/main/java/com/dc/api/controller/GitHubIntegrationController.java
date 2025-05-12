package com.dc.api.controller;

import com.dc.facade.fd.GitHubIntegrationFacade;
import com.domain.dto.GitHubRepoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/github")
public class GitHubIntegrationController {

    private final GitHubIntegrationFacade gitHubIntegrationFacade;

    public GitHubIntegrationController(GitHubIntegrationFacade gitHubIntegrationFacade) {
        this.gitHubIntegrationFacade = gitHubIntegrationFacade;
    }
    
    @PostMapping("/translate")
    public ResponseEntity<String> fetchAndTranslateRepo(@RequestBody GitHubRepoRequest request) {
        System.out.println("👉 Received GitHub request from: " + request.getGitUsername());
        gitHubIntegrationFacade.handleGitHubTranslation(request);
        return ResponseEntity.ok("Translation completed and pushed to GitHub.");
    }
}