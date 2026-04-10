package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UtilisateurMapper;
import com.example.demo.model.Utilisateur;
import com.example.demo.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST pour la gestion des utilisateurs
 * Expose tous les endpoints CRUD et d'authentification
 */
@RestController
@RequestMapping("/api/utilisateurs")
@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs")
public class UtilisateurController {
    
    private final UtilisateurService utilisateurService;
    private final UtilisateurMapper utilisateurMapper;
    
    public UtilisateurController(UtilisateurService utilisateurService, UtilisateurMapper utilisateurMapper) {
        this.utilisateurService = utilisateurService;
        this.utilisateurMapper = utilisateurMapper;
    }
    
    /**
     * Créer un nouvel utilisateur
     */
    @PostMapping
    @Operation(summary = "Créer un utilisateur", description = "Crée un nouvel utilisateur avec validation des données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès",
                    content = @Content(schema = @Schema(implementation = UtilisateurResponse.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "409", description = "Email déjà utilisé")
    })
    public ResponseEntity<UtilisateurResponse> creerUtilisateur(
            @Valid @RequestBody UtilisateurCreateRequest request) {
        
        // Conversion DTO vers Entité
        Utilisateur utilisateur = utilisateurMapper.toEntity(request);
        
        // Création via le service (avec hashage du mot de passe)
        Utilisateur nouveauUtilisateur = utilisateurService.creer(utilisateur);
        
        // Conversion vers DTO de réponse
        UtilisateurResponse response = utilisateurMapper.toResponse(nouveauUtilisateur, "Utilisateur créé avec succès");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Lister tous les utilisateurs
     */
    @GetMapping
    @Operation(summary = "Lister les utilisateurs", description = "Retourne la liste de tous les utilisateurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée",
                    content = @Content(schema = @Schema(implementation = UtilisateurResponse.class)))
    })
    public ResponseEntity<List<UtilisateurResponse>> listerUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.listerTous();
        List<UtilisateurResponse> responses = utilisateurMapper.toResponseList(utilisateurs);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Trouver un utilisateur par son ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Trouver un utilisateur", description = "Retourne un utilisateur par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                    content = @Content(schema = @Schema(implementation = UtilisateurResponse.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UtilisateurResponse> trouverUtilisateur(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Integer id) {
        
        return utilisateurService.trouverParId(id)
                .map(utilisateur -> ResponseEntity.ok(utilisateurMapper.toResponse(utilisateur)))
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
    }
    
    /**
     * Mettre à jour un utilisateur
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un utilisateur", description = "Met à jour les informations d'un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour",
                    content = @Content(schema = @Schema(implementation = UtilisateurResponse.class))),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UtilisateurResponse> mettreAJourUtilisateur(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Integer id,
            @Valid @RequestBody UtilisateurUpdateRequest request) {
        
        // Récupérer l'utilisateur existant
        Utilisateur utilisateur = utilisateurService.trouverParId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        
        // Appliquer les mises à jour
        utilisateurMapper.updateEntity(utilisateur, request);
        
        // Sauvegarder les modifications
        Utilisateur utilisateurMisAJour = utilisateurService.modifier(id, utilisateur);
        
        return ResponseEntity.ok(utilisateurMapper.toResponse(utilisateurMisAJour, "Utilisateur mis à jour avec succès"));
    }
    
    /**
     * Supprimer un utilisateur
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Utilisateur supprimé"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<Void> supprimerUtilisateur(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Integer id) {
        
        if (!utilisateurService.existe(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
        }
        
        utilisateurService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Changer le mot de passe d'un utilisateur
     */
    @PostMapping("/{id}/change-password")
    @Operation(summary = "Changer le mot de passe", description = "Change le mot de passe d'un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mot de passe changé"),
        @ApiResponse(responseCode = "400", description = "Ancien mot de passe incorrect ou nouveau mot de passe invalide"),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<String> changerMotDePasse(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Integer id,
            @Valid @RequestBody PasswordChangeRequest request) {
        
        utilisateurService.changerMotDePasse(id, request.getAncienMotDePasse(), request.getNouveauMotDePasse());
        
        return ResponseEntity.ok("Mot de passe changé avec succès");
    }
    
    /**
     * Réinitialiser le mot de passe d'un utilisateur
     */
    @PostMapping("/{id}/reset-password")
    @Operation(summary = "Réinitialiser le mot de passe", description = "Génère un nouveau mot de passe aléatoire pour un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mot de passe réinitialisé",
                    content = @Content(schema = @Schema(implementation = PasswordResetResponse.class))),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<PasswordResetResponse> reinitialiserMotDePasse(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Integer id) {
        
        String nouveauMotDePasse = utilisateurService.reinitialiserMotDePasse(id);
        
        PasswordResetResponse response = new PasswordResetResponse(
            nouveauMotDePasse, 
            "Nouveau mot de passe généré avec succès"
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Compter le nombre d'utilisateurs
     */
    @GetMapping("/count")
    @Operation(summary = "Compter les utilisateurs", description = "Retourne le nombre total d'utilisateurs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nombre d'utilisateurs récupéré")
    })
    public ResponseEntity<Long> compterUtilisateurs() {
        long count = utilisateurService.compter();
        return ResponseEntity.ok(count);
    }
}
