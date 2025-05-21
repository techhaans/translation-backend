package com.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitHubRepoRequest {

    @NotEmpty(message = "GitHub username is required")
    private String gitUsername;

    @NotEmpty(message = "Personal access token is required")
    private String personalAccessToken;

    @NotEmpty(message = "Repository URL is required")
    private String repoUrl;

    @NotEmpty(message = "Branch is required")
    private String branch;

    @NotEmpty(message = "Package name is required")
    private String packageName;

    @NotEmpty(message = "Page name is required")
    private String pageName;

    @NotEmpty(message = "Dropdown ID is required")
    private String dropdownId;
}
