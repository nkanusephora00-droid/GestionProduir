package com.example.demo.service; // Définit le package du service.

import com.example.demo.model.Produit; // Import de l’entité Produit.
import com.example.demo.repository.ProduitRepository; // Import du dépôt Produit.
import org.springframework.stereotype.Service; // Import de l’annotation Service.

import java.util.List; // Import de la collection List.
import java.util.Optional; // Import de Optional pour la valeur facultative.

@Service // Définit un composant métier pour la logique de service.
public class ProduitService { // Service contenant la logique métier pour les produits.

    private final ProduitRepository produitRepository; // Référence au repository Produit.

    public ProduitService(ProduitRepository produitRepository) { // Constructeur d’injection de dépendance.
        this.produitRepository = produitRepository; // Assigne le repository au champ.
    }

    public List<Produit> listerTous() { // Retourne tous les produits.
        return produitRepository.findAll(); // Appelle la méthode JPA de récupération.
    }

    public Optional<Produit> trouverParId(Long id) { // Cherche un produit par son identifiant.
        return produitRepository.findById(id); // Retourne Optional vide ou produit.
    }

    public Produit creer(Produit produit) { // Créé un nouveau produit.
        return produitRepository.save(produit); // Sauvegarde le produit en base.
    }

    public Produit mettreAJour(Long id, Produit produit) { // Met à jour un produit existant.
        produit.setId(id); // Assure que l’ID existe pour la mise à jour.
        return produitRepository.save(produit); // Sauvegarde et retourne le produit.
    }

    public void supprimer(Long id) { // Supprime un produit par son identifiant.
        produitRepository.deleteById(id); // Appelle la suppression du repository.
    }
}
