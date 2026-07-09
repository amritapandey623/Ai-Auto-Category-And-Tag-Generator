package com.moduleone.products.controller;

import com.moduleone.products.dto.AiResponse;
import com.moduleone.products.dto.ProductRequest;
import com.moduleone.products.model.Product;
import com.moduleone.products.repository.ProductRepository;
import com.moduleone.products.service.AiService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;
    private final AiService aiService;

    public ProductController(ProductRepository repository, AiService aiService) {
        this.repository = repository;
        this.aiService = aiService;
    }

    @PostMapping("/generate-and-save")
    public Product createProduct(@Valid @RequestBody ProductRequest request) {
        AiResponse aiResponse = aiService.generateMetadata(request.getName(), request.getDescription());

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(aiResponse.getCategory());
        product.setSubCategory(aiResponse.getSubCategory());
        product.setSeoTags(aiResponse.getSeoTags());
        product.setFilters(aiResponse.getFilters());
        product.setAiMetadata(aiService.toMetadataMap(aiResponse));

        return repository.save(product);
    }
}
