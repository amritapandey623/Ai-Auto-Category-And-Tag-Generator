package com.moduleone.products.dto;

import java.util.List;

public class AiResponse {
    private String category;
    private String subCategory;
    private List<String> seoTags;
    private List<String> filters;

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
