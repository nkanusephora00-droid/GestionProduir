package com.example.demo.repository;

import com.example.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    
    // Méthode de recherche par email (automatiquement générée par Spring Data JPA)
    Optional<Utilisateur> findByEmail(String email);
    
    // Méthode de recherche par nom (optionnel)
    List<Utilisateur> findByNom(String nom);
    
    // Méthode de recherche par nom et prénom (optionnel)
    List<Utilisateur> findByNomAndPrenom(String nom, String prenom);
    
    // Méthode pour vérifier l'existence par email (optionnel)
    boolean existsByEmail(String email);
}
