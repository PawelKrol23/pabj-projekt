package org.example.serwisogloszen.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long categoryId) {
        super("Category with ID " + categoryId + " not found.");
    }
}