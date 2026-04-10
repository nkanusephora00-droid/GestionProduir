package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @GetMapping
    public String getCategories() {
        return "Liste des catégories - Endpoint à implémenter";
    }

    @GetMapping("/{id}")
    public String getCategorieParId(Long id) {
        return "Catégorie avec ID: " + id + " - Endpoint à implémenter";
    }
}
