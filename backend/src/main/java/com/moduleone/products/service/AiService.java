package com.moduleone.products.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moduleone.products.dto.AiResponse;
import com.moduleone.products.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;

import java.util.*;

@Service
public class AiService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiResponse generateMetadata(String name, String description) {
        try {
            String prompt = String.format("""
                Act as a product metadata expert. Analyze: Name: '%s', Description: '%s'.
                
                Follow these strict rules:
                1. Auto-assign a primary category from this list: [Electronics, Beauty, Home & Kitchen, Fashion, Grocery].
                2. Suggest a specific sub-category.
                3. Generate 5-10 SEO tags.
                4. Suggest sustainability filters (e.g., plastic-free, vegan, organic, recycled).
                
                Return ONLY a JSON object with keys: 
                "category", "subCategory", "seoTags", "filters".
                
                No markdown, no explanation.
                """, name, description);

            Map<String, Object> body = Map.of(
                "model", model,
                "temperature", 0.2,
                "messages", List.of(
                    Map.of("role", "system", "content", "You return strict JSON only."),
                    Map.of("role", "user", "content", prompt)
                ),
                "response_format", Map.of("type", "json_object")
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

            JsonNode json = objectMapper.readTree(response.getBody());
            String rawText = json
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

            return objectMapper.readValue(cleanJson(rawText), AiResponse.class);

        } catch (Exception e) {
            throw new ApiException(HttpStatus.BAD_GATEWAY, "AI metadata generation failed");
        }
    }

    public Map<String, Object> toMetadataMap(AiResponse response) {
        return objectMapper.convertValue(response, Map.class);
    }

    private String cleanJson(String rawText) {
        return rawText.replaceAll("```json|```", "").trim();
    }
}
