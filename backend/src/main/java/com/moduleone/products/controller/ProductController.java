package com.moduleone.products.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moduleone.products.model.Product;
import com.moduleone.products.repository.ProductRepository;
import com.moduleone.products.service.AiService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173") 
public class ProductController {

    private final ProductRepository repository;
    private final AiService aiService;
    private final ObjectMapper mapper = new ObjectMapper();

    public ProductController(ProductRepository repository, AiService aiService) {
        this.repository = repository;
        this.aiService = aiService;
    }

   @PostMapping("/generate-and-save")
public Product createProduct(@RequestBody Product input) {
    
    String aiJson = aiService.generateTags(input.getName(), input.getDescription());

    try {
        JsonNode root = mapper.readTree(aiJson);
        
        
        input.setCategory(root.path("category").asText());
        input.setSubCategory(root.path("subCategory").asText());
        
        List<String> tags = new ArrayList<>();
        root.path("seoTags").forEach(t -> tags.add(t.asText()));
        input.setSeoTags(tags);
        
        List<String> filters = new ArrayList<>();
        root.path("filters").forEach(f -> filters.add(f.asText()));
        input.setFilters(filters);

    } catch (Exception e) {
        e.printStackTrace();
    }

    return repository.save(input); 
}
}