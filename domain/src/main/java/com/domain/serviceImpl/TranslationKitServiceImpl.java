package com.domain.serviceImpl;

import com.domain.service.TranslationKitService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
//@RequiredArgsConstructor
public class TranslationKitServiceImpl implements TranslationKitService {

    private final ResourceLoader resourceLoader;
    public TranslationKitServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public File prepareTranslationKit(String customerId, String customerUId) throws IOException {
        // Load Python template and replace placeholders
        Resource pyTemplateResource = resourceLoader.getResource("classpath:translation-kit/extract_i18n_template.py");
        String pyScript = new String(Files.readAllBytes(pyTemplateResource.getFile().toPath()), StandardCharsets.UTF_8);
        pyScript = pyScript.replace("CUSTOMER_ID_PLACEHOLDER", customerId);
        pyScript = pyScript.replace("CUSTOMER_UID_PLACEHOLDER", customerUId);

        // Load translation.js
        Resource jsResource = resourceLoader.getResource("classpath:translation-kit/translation.js");
        File jsFile = jsResource.getFile();

        // Create temp dir and write files
        File tempDir = Files.createTempDirectory("translation_kit").toFile();
        File pyFile = new File(tempDir, "run_translation.py");
        Files.write(pyFile.toPath(), pyScript.getBytes(StandardCharsets.UTF_8));

        File jsTargetDir = new File(tempDir, "translations");
        jsTargetDir.mkdirs();
        Files.copy(jsFile.toPath(), new File(jsTargetDir, "translation.js").toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Create zip
        File zipFile = new File(tempDir.getAbsolutePath() + ".zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            addToZipFile(pyFile, tempDir, zos);
            addToZipFile(new File(jsTargetDir, "translation.js"), tempDir, zos);
        }

        return zipFile;
    }

    private void addToZipFile(File file, File baseDir, ZipOutputStream zos) throws IOException {
        String zipEntryName = baseDir.toPath().relativize(file.toPath()).toString().replace("\\", "/");
        zos.putNextEntry(new ZipEntry(zipEntryName));
        Files.copy(file.toPath(), zos);
        zos.closeEntry();
    }
}

