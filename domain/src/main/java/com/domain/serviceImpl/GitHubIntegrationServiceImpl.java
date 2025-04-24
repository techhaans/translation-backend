package com.domain.serviceImpl;

import com.domain.dto.GitHubRepoRequest;
import com.domain.service.GitHubIntegrationService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

@Service
public class GitHubIntegrationServiceImpl implements GitHubIntegrationService {

    @Override
    public void processGitHubRepo(GitHubRepoRequest request) {
        // Securely form the authenticated repo URL
        String authenticatedUrl = request.getRepoUrl()
                .replace("https://", "https://" + request.getPersonalAccessToken() + "@");

        // Use a unique temp directory for each operation
        String localPath = "temp/" + UUID.randomUUID();
        File repoDir = new File(localPath);

        try {
            System.out.println("🔄 Cloning repository from: " + authenticatedUrl);
            executeCommand(new String[]{"git", "clone", "-b", request.getBranch(), authenticatedUrl, localPath}, null);

            System.out.println("🔧 Updating remote origin URL to include PAT...");
            executeCommand(new String[]{"git", "remote", "set-url", "origin", authenticatedUrl}, repoDir);

            // Step 2: Run the Python translation script
            String scriptPath = Paths.get("application", "src", "main", "resources", "translation-kit", "extract_i18n_template.py").toString();
            System.out.println("🚀 Running Python translation script...");
            executeCommand(new String[]{"python3", scriptPath, localPath}, null);

            // Step 3: Git operations — add, commit, and push
            System.out.println("✅ Committing translation changes...");
            executeCommand(new String[]{"git", "add", "."}, repoDir);
            executeCommand(new String[]{"git", "commit", "-m", "Auto: Add translations at " + Instant.now()}, repoDir);
            System.out.println("🚚 Pushing changes to GitHub...");
            executeCommand(new String[]{"git", "-c", "credential.helper=", "push", authenticatedUrl, request.getBranch()}, repoDir);

            System.out.println("🎉 Translation process completed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ GitHub processing failed: " + e.getMessage(), e);
        } finally {
            // Optional: Clean up cloned repo to avoid disk clutter
            deleteDirectory(repoDir);
        }
    }

    private void executeCommand(String[] command, File workingDir) throws IOException, InterruptedException {
        // On Windows, replace "python3" with "python"
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            for (int i = 0; i < command.length; i++) {
                if ("python3".equals(command[i])) {
                    command[i] = "python";
                }
            }
        }

        System.out.println(">> Running: " + String.join(" ", command));
        if (workingDir != null) {
            System.out.println(">> In directory: " + workingDir.getAbsolutePath());
        }

        Process process = new ProcessBuilder(command)
                .directory(workingDir)
                .inheritIO()
                .start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed with exit code " + exitCode + ": " + String.join(" ", command));
        }
    }


    private void deleteDirectory(File directoryToBeDeleted) {
        if (directoryToBeDeleted != null && directoryToBeDeleted.exists()) {
            File[] allContents = directoryToBeDeleted.listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    deleteDirectory(file);
                }
            }
            directoryToBeDeleted.delete();
        }
    }
}
