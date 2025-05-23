package com.domain.serviceImpl;

import com.domain.dto.GitHubRepoRequest;
import com.domain.service.GitHubIntegrationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

@Service
public class GitHubIntegrationServiceImpl implements GitHubIntegrationService {

    // === NEW: Inject config values from application.properties ===
    @Value("${git.temp.dir:temp}")
    private String tempDir;

    @Value("${python.interpreter:python3}")
    private String pythonInterpreter;

    @Value("${translation.script.path}")
    private String translationScriptPath;
    @Value("${customer.cuid}")
    private String customerCuid;

    @Override
    public void processGitHubRepo(GitHubRepoRequest request) {
        // === NEW: Construct authenticated repo URL securely (same as before) ===
        String authenticatedUrl = request.getRepoUrl()
                .replace("https://", "https://" + request.getGitUsername() + ":" + request.getPersonalAccessToken() + "@");

        // === NEW: Use injected tempDir + unique UUID ===
        String localPath = tempDir + "/" + UUID.randomUUID();
        File repoDir = new File(localPath);

        try {
            System.out.println("🔄 Cloning repository from: " + authenticatedUrl);
            executeCommand(new String[]{"git", "clone", "-b", request.getBranch(), authenticatedUrl, localPath}, null);

            System.out.println("🔧 Updating remote origin URL to include PAT...");
            executeCommand(new String[]{"git", "remote", "set-url", "origin", authenticatedUrl}, repoDir);

            // === UPDATED: Use translationScriptPath from config ===
            System.out.println("🚀 Running Python translation script...");
            executeCommand(new String[]{
                    resolvePythonInterpreter(),
                    translationScriptPath,
                    localPath,
                    customerCuid,
                    request.getPackageName(),
                    request.getPageName(),
                    request.getDropdownId()
            }, null);

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
            // === NEW: Cleanup cloned repo ===
            deleteDirectory(repoDir);
        }
    }

    private String resolvePythonInterpreter() {
        // === NEW: Detect OS and adjust interpreter ===
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win") && "python3".equals(pythonInterpreter)) {
            return "python";
        }
        return pythonInterpreter;
    }

    private void executeCommand(String[] command, File workingDir) throws IOException, InterruptedException {
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
            if (!directoryToBeDeleted.delete()) {
                System.err.println("Warning: Failed to delete " + directoryToBeDeleted.getAbsolutePath());
            }
        }
    }
}
