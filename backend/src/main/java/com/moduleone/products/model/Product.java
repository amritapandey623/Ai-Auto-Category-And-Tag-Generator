package com.moduleone.products.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;       
    private String subCategory;    
    private List<String> seoTags;  
    private List<String> filters;  
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
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
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getSubCategory() {
        return subCategory;
    }
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
    public List<String> getSeoTags() {
        return seoTags;
    }
    public void setSeoTags(List<String> seoTags) {
        this.seoTags = seoTags;
    }
    public List<String> getFilters() {
        return filters;
    }
    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    
}