package com.example.demo.dto;

import com.example.demo.validation.ValidEmail;
import com.example.demo.validation.ValidPassword;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la mise à jour d'utilisateur (validation des entrées)
 * Tous les champs sont optionnels pour permettre des mises à jour partielles
 */
public class UtilisateurUpdateRequest {
    
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String nom;
    
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String prenom;
    
    @ValidEmail
    private String email;
    
    @ValidPassword
    private String motDePasse;
    
    // Constructeurs
    public UtilisateurUpdateRequest() {}
    
    public UtilisateurUpdateRequest(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }
    
    // Getters et Setters
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
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
