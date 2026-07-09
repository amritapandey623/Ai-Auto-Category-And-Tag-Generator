package com.moduleone.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(max = 180, message = "Product name must be 180 characters or fewer")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 4000, message = "Description must be 4000 characters or fewer")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
