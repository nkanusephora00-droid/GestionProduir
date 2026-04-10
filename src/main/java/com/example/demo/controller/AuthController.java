package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UtilisateurMapper;
import com.example.demo.model.Utilisateur;
import com.example.demo.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST pour l'authentification
 * Expose les endpoints de login et gestion de session
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "API d'authentification des utilisateurs")
public class AuthController {
    
    private final UtilisateurService utilisateurService;
    private final UtilisateurMapper utilisateurMapper;
    
    public AuthController(UtilisateurService utilisateurService, UtilisateurMapper utilisateurMapper) {
        this.utilisateurService = utilisateurService;
        this.utilisateurMapper = utilisateurMapper;
    }
    
    /**
     * Authentifier un utilisateur (login)
     */
    @PostMapping("/login")
    @Operation(summary = "Authentification", description = "Authentifie un utilisateur avec email et mot de passe")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentification réussie",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "401", description = "Email ou mot de passe incorrect"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<AuthResponse> authentifier(
            @Valid @RequestBody AuthRequest authRequest) {
        
        // Authentification via le service
        var utilisateurOpt = utilisateurService.authentifier(authRequest.getEmail(), authRequest.getMotDePasse());
        
        if (utilisateurOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, null, null, "Email ou mot de passe incorrect"));
        }
        
        Utilisateur utilisateur = utilisateurOpt.get();
        
        // TODO: Générer un token JWT ici (pour l'instant, on retourne null)
        AuthResponse response = utilisateurMapper.toAuthResponse(utilisateur, null, "Authentification réussie");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Vérifier si un email existe (pour validation frontend)
     */
    @GetMapping("/check-email/{email}")
    @Operation(summary = "Vérifier un email", description = "Vérifie si un email est déjà utilisé")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Résultat de la vérification"),
        @ApiResponse(responseCode = "400", description = "Email invalide")
    })
    public ResponseEntity<Boolean> verifierEmail(
            @io.swagger.v3.oas.annotations.Parameter(description = "Email à vérifier") 
            @PathVariable String email) {
        
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }
        
        boolean existe = utilisateurService.trouverParEmail(email).isPresent();
        return ResponseEntity.ok(existe);
    }
    
    /**
     * Endpoint de test pour vérifier si le service est fonctionnel
     */
    @GetMapping("/status")
    @Operation(summary = "Status du service", description = "Vérifie si le service d'authentification est fonctionnel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Service fonctionnel")
    })
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Service d'authentification opérationnel");
    }
}
