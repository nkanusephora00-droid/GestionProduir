package com.example.demo.controller; // Définit le package du contrôleur.

import org.springframework.web.bind.annotation.GetMapping; // Import de l’annotation pour les requêtes GET.
import org.springframework.web.bind.annotation.RestController; // Import de l’annotation RestController.

@RestController // Cette classe expose des endpoints REST.
public class AccueilController { // Contrôleur pour la page d’accueil de l’API.

    @GetMapping("/") // Route GET accessible sur la racine du site.
    public String accueil() { // Méthode appelée quand on visite http://localhost:8080/
        return "Bienvenue sur l'API Produit. Utilisez /api/produits pour accéder aux produits."; // Contenu renvoyé au navigateur.
    }
}
