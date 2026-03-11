package com.moduleone.products.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateTags(String name, String description) {
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
                "contents", List.of(
                    Map.of("parts", List.of(
                        Map.of("text", prompt)
                    ))
                )
            );

        
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            

            String url = apiUrl + ":generateContent?key=" + apiKey;

            
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            JsonNode json = objectMapper.readTree(response.getBody());
            String rawText = json
                    .get("candidates")
                    .get(0)
                    .get("content")
                    .get("parts")
                    .get(0)
                    .get("text")
                    .asText();

            
            return rawText.replaceAll("```json|```", "").trim();

        } catch (Exception e) {
            System.err.println("AI Service Error: " + e.getMessage());
            return "{\"category\":\"General\", \"subCategory\":\"Misc\", \"seoTags\":[], \"filters\":[]}";
        }
    }
}