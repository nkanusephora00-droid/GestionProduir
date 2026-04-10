package com.example.demo.dto;

/**
 * DTO pour les réponses utilisateur (masque les données sensibles)
 * Ne contient jamais le mot de passe hashé
 */
public class UtilisateurResponse {
    
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String message;
    
    // Constructeurs
    public UtilisateurResponse() {}
    
    public UtilisateurResponse(Integer id, String nom, String prenom, String email, String message) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.message = message;
    }
    
    public UtilisateurResponse(Integer id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.message = null;
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
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
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
