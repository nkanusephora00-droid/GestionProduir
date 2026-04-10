package com.example.demo.dto;

/**
 * DTO pour la réponse d'authentification
 * Contient les informations de l'utilisateur connecté et un token/message
 */
public class AuthResponse {
    
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String token;
    private String message;
    
    // Constructeurs
    public AuthResponse() {}
    
    public AuthResponse(Integer id, String nom, String prenom, String email, String message) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.message = message;
        this.token = null;
    }
    
    public AuthResponse(Integer id, String nom, String prenom, String email, String token, String message) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.token = token;
        this.message = message;
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
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
