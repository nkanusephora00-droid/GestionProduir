package com.example.demo.dto;

/**
 * DTO pour la réponse de réinitialisation de mot de passe
 * Contient le nouveau mot de passe généré
 */
public class PasswordResetResponse {
    
    private String nouveauMotDePasse;
    private String message;
    
    // Constructeurs
    public PasswordResetResponse() {}
    
    public PasswordResetResponse(String nouveauMotDePasse, String message) {
        this.nouveauMotDePasse = nouveauMotDePasse;
        this.message = message;
    }
    
    // Getters et Setters
    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }
    
    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
