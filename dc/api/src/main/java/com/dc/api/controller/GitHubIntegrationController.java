package com.dc.api.controller;

import com.dc.facade.fd.GitHubIntegrationFacade;
import com.domain.dto.GitHubRepoRequest;
import com.domain.dto.response.ApiResponse;
import com.domain.dto.response.ApiResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(
        name = "GitHub Integration",
        description = "API to fetch a GitHub repository, translate its content, and push changes back to the repository."
)
public class GitHubIntegrationController {

    private final GitHubIntegrationFacade gitHubIntegrationFacade;

    @PostMapping("/translate")
    @Operation(summary = "Translate and Push GitHub Repo", description = "Fetches a GitHub repository, runs translation, and pushes back the result.")
    public ResponseEntity<ApiResponse<String>> fetchAndTranslateRepo(@Valid @RequestBody GitHubRepoRequest request) {
        gitHubIntegrationFacade.handleGitHubTranslation(request);
        return ResponseEntity.ok(ApiResponseBuilder.success(null, "Translation completed and pushed to GitHub."));
    }
}
