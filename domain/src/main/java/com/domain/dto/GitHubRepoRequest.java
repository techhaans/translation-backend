package com.domain.dto;

public class GitHubRepoRequest {
    private String gitUsername;
    private String personalAccessToken;
    private String repoUrl;
    private String branch;
    private String packageName;  // New field for package name
    private String pageName;
    private String dropdownId;   // New field for dropdown ID

    // Getter and Setter methods

    public String getGitUsername() {
        return gitUsername;
    }

    public void setGitUsername(String gitUsername) {
        this.gitUsername = gitUsername;
    }

    public String getPersonalAccessToken() {
        return personalAccessToken;
    }

    public void setPersonalAccessToken(String personalAccessToken) {
        this.personalAccessToken = personalAccessToken;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPackageName() {   // Getter for packageName
        return packageName;
    }

    public void setPackageName(String packageName) {   // Setter for packageName
        this.packageName = packageName;
    }

    public String getDropdownId() {   // Getter for dropdownId
        return dropdownId;
    }

    public void setDropdownId(String dropdownId) {   // Setter for dropdownId
        this.dropdownId = dropdownId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
