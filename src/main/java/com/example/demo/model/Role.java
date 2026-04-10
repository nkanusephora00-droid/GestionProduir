package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant un rôle dans le système
 * Ex: ADMIN, USER, MANAGER, etc.
 */
@Entity(name = "Role")
@Table(name = "roles")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String nom;
    
    @Column(length = 200)
    private String description;
    
    // Plusieurs utilisateurs peuvent avoir le même rôle
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Utilisateur> utilisateurs = new HashSet<>();
    
    // Constructeurs
    public Role() {}
    
    public Role(String nom) {
        this.nom = nom;
    }
    
    public Role(String nom, String description) {
        this.nom = nom;
        this.description = description;
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }
    
    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
    
    // Méthodes utilitaires
    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
        utilisateur.getRoles().add(this);
    }
    
    public void removeUtilisateur(Utilisateur utilisateur) {
        utilisateurs.remove(utilisateur);
        utilisateur.getRoles().remove(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return nom != null && nom.equals(role.nom);
    }
    
    @Override
    public int hashCode() {
        return nom != null ? nom.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
