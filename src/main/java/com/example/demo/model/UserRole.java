package com.example.demo.model;

import jakarta.persistence.*;

/**
 * Entité de jointure pour la relation ManyToMany entre Utilisateur et Role
 * Permet d'ajouter des informations supplémentaires comme la date d'attribution du rôle
 */
@Entity(name = "UserRole")
@Table(name = "user_roles",
       uniqueConstraints = @UniqueConstraint(columnNames = {"utilisateur_id", "role_id"}))
public class UserRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    
    @Column(name = "date_attribution")
    private java.time.LocalDateTime dateAttribution;
    
    @Column(name = "attribue_par")
    private String attribuePar;
    
    // Constructeurs
    public UserRole() {
        this.dateAttribution = java.time.LocalDateTime.now();
    }
    
    public UserRole(Utilisateur utilisateur, Role role) {
        this();
        this.utilisateur = utilisateur;
        this.role = role;
    }
    
    public UserRole(Utilisateur utilisateur, Role role, String attribuePar) {
        this(utilisateur, role);
        this.attribuePar = attribuePar;
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public java.time.LocalDateTime getDateAttribution() {
        return dateAttribution;
    }
    
    public void setDateAttribution(java.time.LocalDateTime dateAttribution) {
        this.dateAttribution = dateAttribution;
    }
    
    public String getAttribuePar() {
        return attribuePar;
    }
    
    public void setAttribuePar(String attribuePar) {
        this.attribuePar = attribuePar;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole)) return false;
        UserRole userRole = (UserRole) o;
        return utilisateur != null && role != null &&
               utilisateur.equals(userRole.utilisateur) &&
               role.equals(userRole.role);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(utilisateur, role);
    }
    
    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", utilisateur=" + (utilisateur != null ? utilisateur.getId() : null) +
                ", role=" + (role != null ? role.getNom() : null) +
                ", dateAttribution=" + dateAttribution +
                '}';
    }
}
