package com.example.demo.dto;

import com.example.demo.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour le changement de mot de passe
 * Contient l'ancien et le nouveau mot de passe
 */
public class PasswordChangeRequest {
    
    @NotBlank(message = "L'ancien mot de passe est obligatoire")
    private String ancienMotDePasse;
    
    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    @ValidPassword
    private String nouveauMotDePasse;
    
    // Constructeurs
    public PasswordChangeRequest() {}
    
    public PasswordChangeRequest(String ancienMotDePasse, String nouveauMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
    
    // Getters et Setters
    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }
    
    public void setAncienMotDePasse(String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }
    
    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }
    
    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
}
