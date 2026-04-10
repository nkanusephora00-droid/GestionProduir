package com.example.demo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Utilisateur")
@Table(name = "utilisateurs")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 50)
    private String nom;
    
    @Column(nullable = false, length = 50)
    private String prenom;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String motDePasse;
    
    // Relation ManyToMany avec Role
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "utilisateur_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"utilisateur_id", "role_id"})
    )
    private Set<Role> roles = new HashSet<>();
    
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
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    // Méthodes utilitaires pour la gestion des rôles
    public void addRole(Role role) {
        roles.add(role);
        role.getUtilisateurs().add(this);
    }
    
    public void removeRole(Role role) {
        roles.remove(role);
        role.getUtilisateurs().remove(this);
    }
    
    public boolean hasRole(String roleName) {
        return roles.stream()
                .anyMatch(role -> role.getNom().equals(roleName));
    }
    
    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
    
    // Méthode pour vérifier si l'utilisateur est admin
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }
    
    // Méthode pour vérifier si l'utilisateur est manager
    public boolean isManager() {
        return hasRole("MANAGER") || isAdmin();
    }
}
