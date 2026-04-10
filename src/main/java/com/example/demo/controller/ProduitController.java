package com.example.demo.controller; // Définit le package du contrôleur.

import com.example.demo.dto.ProduitResponse; // Import du DTO ProduitResponse.
import com.example.demo.model.Produit; // Import de l'entité Produit.
import com.example.demo.service.ProduitService; // Import du service Produit.
import org.springframework.http.HttpStatus; // Import de HttpStatus pour les codes de statut.
import org.springframework.http.ResponseEntity; // Import de ResponseEntity pour les réponses HTTP.
import org.springframework.web.bind.annotation.CrossOrigin; // Import de l'annotation CORS.
import org.springframework.web.bind.annotation.*; // Import des annotations REST.

import java.util.List; // Import de la collection List.

@RestController // Indique que cette classe expose des API REST.
@CrossOrigin(origins = "http://localhost:3000") // Autorise le frontend sur http://localhost:3000 à appeler ces routes.
@RequestMapping("/api/produits") // Préfixe toutes les routes de ce contrôleur.
public class ProduitController { // Contrôleur REST pour gérer les produits.

    private final ProduitService produitService; // Service métier injecté.

    public ProduitController(ProduitService produitService) { // Constructeur avec injection du service.
        this.produitService = produitService; // Assigne le service au champ.
    }

    @GetMapping // Route GET pour récupérer tous les produits.
    public List<Produit> getTousLesProduits() { // Méthode qui retourne la liste des produits.
        return produitService.listerTous(); // Appelle le service pour obtenir les produits.
    }

    @GetMapping("/{id}") // Route GET pour récupérer un produit par ID.
    public ResponseEntity<Produit> getProduitParId(@PathVariable Long id) { // ID lu depuis l’URL.
        return produitService.trouverParId(id) // Recherche le produit par ID.
                .map(ResponseEntity::ok) // Si trouvé, retourne 200 OK avec le produit.
                .orElse(ResponseEntity.notFound().build()); // Sinon, retourne 404 Not Found.
    }

    @PostMapping // Route POST pour créer un nouveau produit.
    public ResponseEntity<ProduitResponse> creerProduit(@RequestBody Produit produit) { // Reçoit le produit depuis le corps de la requête.
        produit.setId(null); // Force l'ID à null pour éviter les conflits avec l'auto-génération
        Produit nouveauProduit = produitService.creer(produit); // Appelle le service pour sauvegarder le produit.
        ProduitResponse response = new ProduitResponse(
            nouveauProduit.getId(), 
            nouveauProduit.getNom(), 
            nouveauProduit.getPrix(), 
            "Produit créé avec succès"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Retourne 201 Created avec le DTO personnalisé
    }

    @PutMapping("/{id}") // Route PUT pour mettre à jour un produit existant.
    public ResponseEntity<Produit> mettreAJourProduit(@PathVariable Long id, @RequestBody Produit produit) { // ID de l’URL et données du corps.
        if (produitService.trouverParId(id).isEmpty()) { // Vérifie si le produit existe.
            return ResponseEntity.notFound().build(); // Retourne 404 si non trouvé.
        }
        return ResponseEntity.ok(produitService.mettreAJour(id, produit)); // Met à jour et retourne 200 OK.
    }

    @DeleteMapping("/{id}") // Route DELETE pour supprimer un produit.
    public ResponseEntity<Void> supprimerProduit(@PathVariable Long id) { // ID de l’URL.
        if (produitService.trouverParId(id).isEmpty()) { // Vérifie si le produit existe.
            return ResponseEntity.notFound().build(); // Retourne 404 si non trouvé.
        }
        produitService.supprimer(id); // Supprime le produit.
        return ResponseEntity.noContent().build(); // Retourne 204 No Content.
    }
}
