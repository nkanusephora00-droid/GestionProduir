package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour l'authentification
 * Contient uniquement les identifiants de connexion
 */
public class AuthRequest {
    
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
    
    // Constructeurs
    public AuthRequest() {}
    
    public AuthRequest(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }
    
    // Getters et Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
