package com.domain.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Component
public class ChatGptTranslator {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private static final int CHUNK_SIZE = 30;

    public ChatGptTranslator() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public String translateText(String text, String fromLang, String toLang) {
        String prompt = String.format("Translate this text from %s to %s: \"%s\"", fromLang, toLang, text);

        Map<String, Object> message = Map.of("role", "user", "content", prompt);
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4",
                "messages", List.of(message)
        );

        try {
            Map response = webClient.post()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response == null) return "TRANSLATION_FAILED";

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices == null || choices.isEmpty()) return "TRANSLATION_FAILED";

            Map<String, String> messageContent = (Map<String, String>) choices.get(0).get("message");
            return messageContent.get("content").trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "TRANSLATION_FAILED";
        }
    }

    public Map<String, String> translateBatch(Map<String, String> labels, String fromLang, String toLang) {
        Map<String, String> result = new LinkedHashMap<>();
        List<Map<String, String>> chunks = splitIntoChunks(labels, CHUNK_SIZE);

        for (Map<String, String> chunk : chunks) {
            try {
                String jsonChunk = objectMapper.writeValueAsString(chunk);
                String prompt = String.format("Translate the following JSON object from %s to %s and return only valid JSON:\n%s",
                        fromLang, toLang, jsonChunk);

                Map<String, Object> message = Map.of("role", "user", "content", prompt);
                Map<String, Object> requestBody = Map.of(
                        "model", "gpt-4",
                        "messages", List.of(message)
                );

                Map response = webClient.post()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey)
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

                if (response == null) continue;

                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (choices == null || choices.isEmpty()) continue;

                String content = ((Map<String, String>) choices.get(0).get("message")).get("content").trim();

                Map<String, String> chunkResult = objectMapper.readValue(content, Map.class);
                result.putAll(chunkResult);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private List<Map<String, String>> splitIntoChunks(Map<String, String> labels, int chunkSize) {
        List<Map<String, String>> chunks = new ArrayList<>();
        Map<String, String> currentChunk = new LinkedHashMap<>();
        int count = 0;

        for (Map.Entry<String, String> entry : labels.entrySet()) {
            currentChunk.put(entry.getKey(), entry.getValue());
            count++;

            if (count == chunkSize) {
                chunks.add(currentChunk);
                currentChunk = new LinkedHashMap<>();
                count = 0;
            }
        }

        if (!currentChunk.isEmpty()) {
            chunks.add(currentChunk);
        }

        return chunks;
    }
}
