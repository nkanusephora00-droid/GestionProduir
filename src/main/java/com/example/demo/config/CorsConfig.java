package com.example.demo.config; // Définit le package de configuration.

import org.springframework.context.annotation.Configuration; // Import de l’annotation Configuration.
import org.springframework.web.servlet.config.annotation.CorsRegistry; // Import pour configurer CORS.
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // Import de l’interface de configuration MVC.

@Configuration // Indique que cette classe contient des configurations Spring.
public class CorsConfig implements WebMvcConfigurer { // Classe de configuration CORS.

    @Override
    public void addCorsMappings(CorsRegistry registry) { // Méthode appelée par Spring pour configurer CORS.
        registry.addMapping("/api/**") // Autorise les requêtes sur toutes les routes commençant par /api/.
                .allowedOrigins("*") // Autorise toutes les origines (pour le déploiement en ligne)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Autorise ces méthodes HTTP.
                .allowedHeaders("*") // Autorise tous les en-têtes.
                .allowCredentials(false) // Ne pas autoriser l’envoi des cookies.
                .maxAge(3600); // Durée de validité de la pré-vérification CORS en secondes.
    }
}
