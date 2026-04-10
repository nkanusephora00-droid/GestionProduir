package com.example.demo.config; // Définit le package de configuration.

import io.swagger.v3.oas.models.OpenAPI; // Import de la classe OpenAPI.
import io.swagger.v3.oas.models.info.Info; // Import des informations du document.
import org.springframework.context.annotation.Bean; // Import de l’annotation Bean.
import org.springframework.context.annotation.Configuration; // Import de l’annotation Configuration.

@Configuration // Indique que cette classe contient des configurations Spring.
public class OpenApiConfig { // Configuration OpenAPI / Swagger.

    @Bean // Crée un bean Spring au démarrage.
    public OpenAPI demoOpenAPI() { // Définit les informations de l’API.
        return new OpenAPI() // Crée la configuration OpenAPI.
                .info(new Info() // Définit le titre et la description.
                        .title("API Produit")
                        .description("Documentation Swagger de l’API Produit")
                        .version("v1"));
    }
}
