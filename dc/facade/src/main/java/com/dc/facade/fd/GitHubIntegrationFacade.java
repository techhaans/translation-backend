package com.dc.facade.fd;

import com.domain.dto.GitHubRepoRequest;

public interface GitHubIntegrationFacade {
    void handleGitHubTranslation(GitHubRepoRequest request);
}
