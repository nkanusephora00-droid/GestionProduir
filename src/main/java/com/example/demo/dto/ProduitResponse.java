package com.example.demo.dto;

public class ProduitResponse {
   
    private Long id;
    private String nom;
    private Double prix;
    private String message;

    // Constructeurs
    public ProduitResponse() {}

    public ProduitResponse(Long id, String nom, Double prix, String message) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.message = message;
    }
 // Getters et Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
