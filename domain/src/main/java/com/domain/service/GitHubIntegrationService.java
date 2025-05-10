package com.domain.service;

import com.domain.dto.GitHubRepoRequest;

public interface GitHubIntegrationService {
    void processGitHubRepo(GitHubRepoRequest request);
}
