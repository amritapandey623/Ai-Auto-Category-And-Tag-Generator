package com.moduleone.products.service;

import com.moduleone.products.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class ProductService {

    private final String API_KEY = "AIzaSyBguNKX9qVxdVzK0liC8lRovUc0BgRyljo";

    public Product createProduct(Product product) throws Exception {

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();

        String prompt = "Write a short attractive product description for: " + product.getName();

        Map<String, Object> text = new HashMap<>();
        text.put("text", prompt);

        List<Map<String, Object>> parts = new ArrayList<>();
        parts.add(text);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", parts);

        List<Map<String, Object>> contents = new ArrayList<>();
        contents.add(content);

        Map<String, Object> body = new HashMap<>();
        body.put("contents", contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        try {

            List candidates = (List) response.getBody().get("candidates");
            Map candidate = (Map) candidates.get(0);

            Map contentMap = (Map) candidate.get("content");
            List partsList = (List) contentMap.get("parts");

            Map textPart = (Map) partsList.get(0);

            String description = textPart.get("text").toString();

            product.setDescription(description);

        } catch (Exception e) {
            product.setDescription("AI description could not be generated.");
        }

        return product;
    }
}