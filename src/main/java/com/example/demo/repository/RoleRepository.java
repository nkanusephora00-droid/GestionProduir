package com.example.demo.repository;

import com.example.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    // Trouver un rôle par son nom
    Optional<Role> findByNom(String nom);
    
    // Vérifier si un rôle existe par son nom
    boolean existsByNom(String nom);
    
    // Trouver des rôles par nom (recherche partielle)
    List<Role> findByNomContainingIgnoreCase(String nom);
    
    // Compter les utilisateurs par rôle
    @Query("SELECT COUNT(u) FROM Utilisateur u JOIN u.roles r WHERE r.nom = :roleNom")
    long countUtilisateursByRole(@Param("roleNom") String roleNom);
    
    // Lister tous les rôles avec le nombre d'utilisateurs
    @Query("SELECT r, COUNT(u) FROM Role r LEFT JOIN r.utilisateurs u GROUP BY r.id ORDER BY r.nom")
    List<Object[]> findAllWithUserCount();
    
    // Trouver les rôles d'un utilisateur
    @Query("SELECT r FROM Role r JOIN r.utilisateurs u WHERE u.id = :utilisateurId")
    List<Role> findByUtilisateurId(@Param("utilisateurId") Integer utilisateurId);
}
