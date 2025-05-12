package com.dc.facadeImpl.fi;

import com.dc.facade.fd.GitHubIntegrationFacade;
import com.domain.dto.GitHubRepoRequest;
import com.domain.service.GitHubIntegrationService;
import org.springframework.stereotype.Component;

@Component
public class GitHubIntegrationFacadeImpl implements GitHubIntegrationFacade {

    private final GitHubIntegrationService gitHubIntegrationService;

    public GitHubIntegrationFacadeImpl(GitHubIntegrationService gitHubIntegrationService) {
        this.gitHubIntegrationService = gitHubIntegrationService;
    }

    @Override
    public void handleGitHubTranslation(GitHubRepoRequest request) {
        gitHubIntegrationService.processGitHubRepo(request);
    }
}