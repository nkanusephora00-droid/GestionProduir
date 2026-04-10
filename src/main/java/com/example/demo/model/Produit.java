package com.example.demo.model; // Définit le package de l’entité Produit.

import jakarta.persistence.Entity; // Import JPA de l’annotation @Entity.
import jakarta.persistence.GeneratedValue; // Import pour la génération automatique de l’ID.
import jakarta.persistence.GenerationType; // Import pour les stratégies de génération d’ID.
import jakarta.persistence.Id; // Import pour marquer la clé primaire.

@Entity // Indique que cette classe est une entité JPA mappée à une table de base de données.
public class Produit { // Représente la table "produit" en Java.

    @Id // Clé primaire de la table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Valeur auto-générée par la base.
    private Long id; // Identifiant unique du produit.

    private String nom; // Nom du produit.
    private Double prix; // Prix du produit.

    public Produit() { // Constructeur sans arguments requis par JPA.
        // Aucun code nécessaire ici.
    }

    public Produit(String nom, Double prix) { // Constructeur avec initialisation des champs.
        this.nom = nom; // Assigne le nom du produit.
        this.prix = prix; // Assigne le prix du produit.
    }

    public Long getId() { // Retourne l’identifiant du produit.
        return id;
    }

    public void setId(Long id) { // Définit l’identifiant du produit.
        this.id = id;
    }

    public String getNom() { // Retourne le nom du produit.
        return nom;
    }

    public void setNom(String nom) { // Définit le nom du produit.
        this.nom = nom;
    }

    public Double getPrix() { // Retourne le prix du produit.
        return prix;
    }

    public void setPrix(Double prix) { // Définit le prix du produit.
        this.prix = prix;
    }
}
