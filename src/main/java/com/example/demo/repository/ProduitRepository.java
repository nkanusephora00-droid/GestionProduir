package com.example.demo.repository; // Définit le package du dépôt.

import com.example.demo.model.Produit; // Import de l’entité Produit.
import org.springframework.data.jpa.repository.JpaRepository; // Import de l’interface JPA Repository.
import org.springframework.stereotype.Repository; // Import de l’annotation Repository.

@Repository // Spécifie que cette interface est un composant de persistance.
public interface ProduitRepository extends JpaRepository<Produit, Long> { // Interface de repository pour Produit.
    // JpaRepository fournit déjà les méthodes CRUD de base.
}
